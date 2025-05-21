const express = require('express');
const app = express();

// In-memory storage (replace with a database in production)
let items = [
  { id: 1, name: 'First item', completed: false },
  { id: 2, name: 'Second item', completed: true }
];

// Middleware
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Basic route
app.get('/', (req, res) => {
  res.json({ message: 'Welcome to Express API' });
});

// GET all items
app.get('/api/items', (req, res) => {
  res.json(items);
});

// GET single item
app.get('/api/items/:id', (req, res) => {
  const item = items.find(i => i.id === parseInt(req.params.id));
  if (!item) return res.status(404).json({ message: 'Item not found' });
  res.json(item);
});

// POST new item
app.post('/api/items', (req, res) => {
  if (!req.body.name) {
    return res.status(400).json({ message: 'Name is required' });
  }
  
  const newItem = {
    id: items.length + 1,
    name: req.body.name,
    completed: req.body.completed || false
  };
  
  items.push(newItem);
  res.status(201).json(newItem);
});

// PUT update item
app.put('/api/items/:id', (req, res) => {
  const item = items.find(i => i.id === parseInt(req.params.id));
  if (!item) return res.status(404).json({ message: 'Item not found' });

  item.name = req.body.name || item.name;
  item.completed = req.body.completed !== undefined ? req.body.completed : item.completed;
  
  res.json(item);
});

// DELETE item
app.delete('/api/items/:id', (req, res) => {
  const itemIndex = items.findIndex(i => i.id === parseInt(req.params.id));
  if (itemIndex === -1) return res.status(404).json({ message: 'Item not found' });

  items = items.filter(i => i.id !== parseInt(req.params.id));
  res.status(204).send();
});

// Error handling middleware
app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).json({ error: 'Something went wrong!' });
});

module.exports = app;