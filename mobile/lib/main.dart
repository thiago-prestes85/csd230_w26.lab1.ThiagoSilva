import 'package:flutter/material.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: BookPage(),
    );
  }
}

class BookPage extends StatefulWidget {
  @override
  _BookPageState createState() => _BookPageState();
}

class _BookPageState extends State<BookPage> {
  List books = [];

  @override
  void initState() {
    super.initState();
    fetchBooks();
  }

  fetchBooks() async {
    final response = await http.get(
      Uri.parse('http://10.0.2.2:8080/api/rest/books'),
    );

    if (response.statusCode == 200) {
      setState(() {
        books = json.decode(response.body);
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Books")),
      body: ListView.builder(
        itemCount: books.length,
        itemBuilder: (context, index) {
          return ListTile(
            title: Text(books[index]['title']),
            subtitle: Text("\$${books[index]['pubPrice']}"),
          );
        },
      ),
    );
  }
}