import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';

class StressFormScreen extends StatefulWidget {
  @override
  _StressFormScreenState createState() => _StressFormScreenState();
}

class _StressFormScreenState extends State<StressFormScreen> {
  final _formKey = GlobalKey<FormState>();
  String? stressCause;
  String? stressLevel;
  final List<String> stressLevels = ["Low", "Medium", "High"];
  final Map<String, String> suggestions = {
    "Low": "Try a short walk or listen to your favorite music 🎵",
    "Medium": "Do some breathing exercises or meditate 🧘",
    "High": "Take a break, call a friend, or seek support 💬",
  };

  void _submitForm() {
    if (_formKey.currentState!.validate()) {
      _formKey.currentState!.save();

      FirebaseFirestore.instance.collection('stress_entries').add({
        'stressCause': stressCause,
        'stressLevel': stressLevel,
        'timestamp': Timestamp.now(),
      });

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text("Stress data saved successfully!")),
      );

      Navigator.pop(context);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Stress Details'),
        backgroundColor: Colors.deepPurple,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: Column(
            children: [
              DropdownButtonFormField<String>(
                decoration: const InputDecoration(
                  labelText: "Select Stress Level",
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
                        value == null ? "Please select a stress level" : null,
              ),
              TextFormField(
                decoration: const InputDecoration(
                  labelText: "What caused your stress?",
                ),
                onSaved: (value) {
                  stressCause = value!;
                },
                validator:
                    (value) => value!.isEmpty ? "Please enter a cause" : null,
              ),
              const SizedBox(height: 20),
              if (stressLevel != null)
                Text(
                  "💡 Suggestion: ${suggestions[stressLevel]!}",
                  style: const TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.bold,
                    color: Colors.deepPurple,
                  ),
                ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: _submitForm,
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.deepPurpleAccent,
                ),
                child: const Text(
                  "Submit",
                  style: TextStyle(color: Colors.white),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
