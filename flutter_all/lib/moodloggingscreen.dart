import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';

class MoodLoggingScreen extends StatelessWidget {
  final FirebaseFirestore _firestore = FirebaseFirestore.instance;

  Future<void> _saveStressLevel(BuildContext context, String level) async {
    try {
      await _firestore.collection('stressLevels').add({
        'level': level,
        'timestamp': FieldValue.serverTimestamp(),
      });

      // Show success message
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text("Logged $level Stress Successfully!")),
      );

      // Close the screen after saving
      Future.delayed(Duration(seconds: 1), () {
        Navigator.pop(context);
      });
    } catch (e) {
      print("Error saving stress level: $e");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Log Your Mood")),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ElevatedButton(
              onPressed: () => _saveStressLevel(context, "Low"),
              child: const Text("Low Stress"),
            ),
            ElevatedButton(
              onPressed: () => _saveStressLevel(context, "Medium"),
              child: const Text("Medium Stress"),
            ),
            ElevatedButton(
              onPressed: () => _saveStressLevel(context, "High"),
              child: const Text("High Stress"),
            ),
          ],
        ),
      ),
    );
  }
}
