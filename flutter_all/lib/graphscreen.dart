import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:fl_chart/fl_chart.dart';

class GraphScreen extends StatelessWidget {
  const GraphScreen({super.key});

  Future<List<FlSpot>> _getStressData() async {
    final snapshot =
        await FirebaseFirestore.instance
            .collection('stressLevels')
            .orderBy('timestamp')
            .get();

    List<FlSpot> spots = [];
    for (int i = 0; i < snapshot.docs.length; i++) {
      var doc = snapshot.docs[i];
      int level = _getStressLevelValue(doc['level']);
      spots.add(FlSpot(i.toDouble(), level.toDouble()));
    }
    return spots;
  }

  int _getStressLevelValue(String level) {
    switch (level) {
      case "Low":
        return 1;
      case "Medium":
        return 2;
      case "High":
        return 3;
      default:
        return 0;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Stress Trends')),
      body: FutureBuilder<List<FlSpot>>(
        future: _getStressData(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }
          if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text('No data available'));
          }
          return Padding(
            padding: const EdgeInsets.all(16.0),
            child: LineChart(
              LineChartData(
                titlesData: FlTitlesData(
                  leftTitles: AxisTitles(
                    sideTitles: SideTitles(
                      showTitles: true,
                      getTitlesWidget: (value, meta) {
                        switch (value.toInt()) {
                          case 1:
                            return const Text("Low");
                          case 2:
                            return const Text("Medium");
                          case 3:
                            return const Text("High");
                          default:
                            return const Text("");
                        }
                      },
                    ),
                  ),
                  bottomTitles: AxisTitles(
                    sideTitles: SideTitles(showTitles: false),
                  ),
                ),
                borderData: FlBorderData(show: true),
                lineBarsData: [
                  LineChartBarData(
                    spots: snapshot.data!,
                    isCurved: true,
                    color: Colors.deepPurple,
                    belowBarData: BarAreaData(
                      show: true,
                      color: Colors.deepPurple.withOpacity(0.3),
                    ),
                  ),
                ],
              ),
            ),
          );
        },
      ),
    );
  }
}
