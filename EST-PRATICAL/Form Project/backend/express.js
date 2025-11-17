import express from "express";
import cors from "cors";

const app = express();
const port = 3000;

// Middlewares
app.use(cors());               
app.use(express.json());   

app.post('/',(req, res)=>{
    res.send("Server running...")
})

app.post("/posts", (req, res) => {
  const { title, body } = req.body;

  console.log("Received data:", title, body);
  res.status(201).json({
    message: "Post created successfully",
    data: { title, body },
  });
});

app.listen(port, () => {
  console.log(`Server running on port ${port}`);
});
