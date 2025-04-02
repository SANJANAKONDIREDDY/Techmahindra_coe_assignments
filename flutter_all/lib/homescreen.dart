import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:intl/intl.dart';
import 'package:fl_chart/fl_chart.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  final _formKey = GlobalKey<FormState>();
  String? stressCause;
  String? stressLevel;
  DateTime selectedDate = DateTime.now();
  String selectedLanguage = "English";

  final List<String> stressLevels = ["Low", "Medium", "High"];
  final Map<String, Map<String, String>> suggestions = {
    "English": {
      "Low": "🎵 Try listening to music or taking a short walk.",
      "Medium": "🧘 Practice deep breathing or mindfulness meditation.",
      "High": "💬 Talk to a friend, take a break, or try journaling.",
    },
    "Telugu": {
      "Low": "🎵 సంగీతాన్ని వినండి లేదా చిన్న నడక చేయండి.",
      "Medium":
          "🧘 లోతైన శ్వాసను అభ్యాసం చేయండి లేదా మైండ్ఫుల్నెస్ ధ్యానం చేయండి.",
      "High":
          "💬 స్నేహితునితో మాట్లాడండి, విరామం తీసుకోండి లేదా జర్నలింగ్ చేయండి.",
    },
  };

  void _changeLanguage() {
    setState(() {
      selectedLanguage = selectedLanguage == "English" ? "Telugu" : "English";
    });
  }

  void _submitForm() {
    if (_formKey.currentState!.validate()) {
      _formKey.currentState!.save();
      FirebaseFirestore.instance.collection('stress_entries').add({
        'stressCause': stressCause,
        'stressLevel': stressLevel,
        'timestamp': Timestamp.fromDate(selectedDate),
      });
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(
            selectedLanguage == "English"
                ? "✅ Stress data saved successfully!"
                : "✅ ఒత్తిడి డేటా విజయవంతంగా భద్రపరచబడింది!",
          ),
        ),
      );
      setState(() {
        stressCause = null;
        stressLevel = null;
        selectedDate = DateTime.now();
      });
      Navigator.pop(context);
    }
  }

  void _deleteEntry(String docId) {
    FirebaseFirestore.instance.collection('stress_entries').doc(docId).delete();
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(
          selectedLanguage == "English"
              ? "🗑️ Stress entry deleted!"
              : "🗑️ ఒత్తిడి ఎంట్రీ తొలగించబడింది!",
        ),
      ),
    );
  }

  void _selectDate(BuildContext context) async {
    DateTime? picked = await showDatePicker(
      context: context,
      initialDate: selectedDate,
      firstDate: DateTime(2020),
      lastDate: DateTime.now(),
    );
    if (picked != null) {
      setState(() {
        selectedDate = picked;
      });
    }
  }

  Widget _buildGraph(List<QueryDocumentSnapshot> documents) {
    Map<String, int> stressCount = {"Low": 0, "Medium": 0, "High": 0};
    for (var doc in documents) {
      String level = doc['stressLevel'];
      stressCount[level] = (stressCount[level] ?? 0) + 1;
    }
    return SizedBox(
      height: 200,
      child: BarChart(
        BarChartData(
          alignment: BarChartAlignment.spaceAround,
          barGroups:
              stressCount.entries.map((entry) {
                return BarChartGroupData(
                  x: stressLevels.indexOf(entry.key),
                  barRods: [
                    BarChartRodData(
                      toY: entry.value.toDouble(),
                      color: Colors.deepPurple,
                      width: 20,
                    ),
                  ],
                );
              }).toList(),
        ),
      ),
    );
  }

  Widget _getStressIcon(String level) {
    switch (level.toLowerCase()) {
      case "low":
        return const Icon(Icons.sentiment_satisfied, color: Colors.green);
      case "medium":
        return const Icon(Icons.sentiment_neutral, color: Colors.orange);
      case "high":
        return const Icon(Icons.sentiment_very_dissatisfied, color: Colors.red);
      default:
        return const Icon(Icons.help_outline, color: Colors.grey);
    }
  }

  void _showStressForm() {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      builder: (context) {
        return Padding(
          padding: EdgeInsets.only(
            bottom: MediaQuery.of(context).viewInsets.bottom,
          ),
          child: Form(
            key: _formKey,
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                const SizedBox(height: 10),
                TextFormField(
                  readOnly: true,
                  decoration: InputDecoration(
                    labelText:
                        selectedLanguage == "English"
                            ? "Select Date"
                            : "తేదీ ఎంచుకోండి",
                    suffixIcon: const Icon(Icons.calendar_today),
                  ),
                  controller: TextEditingController(
                    text: DateFormat('yyyy-MM-dd').format(selectedDate),
                  ),
                  onTap: () => _selectDate(context),
                ),
                const SizedBox(height: 10),
                DropdownButtonFormField<String>(
                  decoration: InputDecoration(
                    labelText:
                        selectedLanguage == "English"
                            ? "Select Stress Level"
                            : "ఒత్తిడి స్థాయిని ఎంచుకోండి",
                  ),
                  items:
                      stressLevels.map((level) {
                        return DropdownMenuItem<String>(
                          value: level,
                          child: Text(level),
                        );
                      }).toList(),
                  onChanged: (value) {
                    setState(() {
                      stressLevel = value!;
                    });
                  },
                  validator:
                      (value) =>
                          value == null
                              ? selectedLanguage == "English"
                                  ? "Please select a stress level"
                                  : "ఒత్తిడి స్థాయిని ఎంచుకోండి"
                              : null,
                ),
                const SizedBox(height: 10),
                TextFormField(
                  decoration: InputDecoration(
                    labelText:
                        selectedLanguage == "English"
                            ? "What caused your stress?"
                            : "మీ ఒత్తిడికి కారణమైనది ఏమిటి?",
                  ),
                  onSaved: (value) {
                    stressCause = value!;
                  },
                  validator:
                      (value) =>
                          value!.isEmpty
                              ? selectedLanguage == "English"
                                  ? "Please enter a cause"
                                  : "కారణాన్ని నమోదు చేయండి"
                              : null,
                ),
                const SizedBox(height: 10),
                if (stressLevel != null)
                  Text(
                    "💡 Suggestion: ${suggestions[selectedLanguage]![stressLevel!]}",
                    style: const TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.bold,
                      color: Colors.deepPurple,
                    ),
                  ),
                const SizedBox(height: 10),
                ElevatedButton(
                  onPressed: _submitForm,
                  child: Text(
                    selectedLanguage == "English" ? "Submit" : "సమర్పించండి",
                    style: const TextStyle(color: Colors.white),
                  ),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.deepPurpleAccent,
                  ),
                ),
                const SizedBox(height: 10),
              ],
            ),
          ),
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    String formattedDate = DateFormat('yyyy-MM-dd').format(DateTime.now());
    return Scaffold(
      appBar: AppBar(
        title: Text(
          selectedLanguage == "English"
              ? 'StressOmeter - $formattedDate'
              : 'ఒత్తిడి మీటర్ - $formattedDate',
        ),
        centerTitle: true,
        backgroundColor: Colors.deepPurple,
        actions: [
          IconButton(
            icon: const Icon(Icons.language),
            onPressed: _changeLanguage,
          ),
        ],
      ),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const SizedBox(height: 20),
          Center(
            child: Image.asset(
              'assets/images/stress1.jpeg',
              width: 120,
              height: 120,
            ),
          ),
          const SizedBox(height: 20),
          Center(
            child: ElevatedButton(
              onPressed: _showStressForm,
              child: Text(
                selectedLanguage == "English"
                    ? "How Stressed Are We Today?"
                    : "ఈ రోజు మనం ఎంత ఒత్తిడిలో ఉన్నాము?",
                style: const TextStyle(color: Colors.white),
              ),
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.deepPurpleAccent,
              ),
            ),
          ),
          const SizedBox(height: 20),
          Expanded(
            child: StreamBuilder<QuerySnapshot>(
              stream:
                  FirebaseFirestore.instance
                      .collection('stress_entries')
                      .orderBy('timestamp', descending: true)
                      .snapshots(),
              builder: (context, snapshot) {
                if (!snapshot.hasData) {
                  return const Center(child: CircularProgressIndicator());
                }
                var documents = snapshot.data!.docs;
                if (documents.isEmpty) {
                  return const Center(child: Text("No past data found."));
                }
                return Column(
                  children: [
                    _buildGraph(documents),
                    Expanded(
                      child: ListView.builder(
                        itemCount: documents.length,
                        itemBuilder: (context, index) {
                          var data = documents[index];
                          return ListTile(
                            leading: _getStressIcon(data['stressLevel']),
                            title: Text(
                              selectedLanguage == "English"
                                  ? "Stress Level: ${data['stressLevel']}"
                                  : "ఒత్తిడి స్థాయి: ${data['stressLevel']}",
                            ),
                            subtitle: Text(
                              selectedLanguage == "English"
                                  ? "Cause: ${data['stressCause']}"
                                  : "కారణం: ${data['stressCause']}",
                            ),
                            trailing: Row(
                              mainAxisSize: MainAxisSize.min,
                              children: [
                                Text(
                                  DateFormat(
                                    'yyyy-MM-dd',
                                  ).format(data['timestamp'].toDate()),
                                ),
                                IconButton(
                                  icon: const Icon(
                                    Icons.delete,
                                    color: Colors.red,
                                  ),
                                  onPressed: () => _deleteEntry(data.id),
                                ),
                              ],
                            ),
                          );
                        },
                      ),
                    ),
                  ],
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}
