const express = require('express');
const path = require('path');
const app = express();

const PORT = 80; // You can change this if needed

// Replace 'your-project-name' with your actual Angular project name inside dist/
const distPath = path.join(__dirname, 'dist', 'your-project-name');

// Serve static files from Angular build
app.use(express.static(distPath));

// Redirect all other routes to index.html (for Angular routing to work)
app.get('*', (req, res) => {
  res.sendFile(path.join(distPath, 'index.html'));
});

app.listen(PORT, () => {
  console.log(`UI server running at http://localhost:${PORT}`);
});

