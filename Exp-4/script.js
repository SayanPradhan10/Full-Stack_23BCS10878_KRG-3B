// State
let circles = [];
let selectedColor = "hsl(262,80%,55%)";
let isDrawing = true;
let idCounter = 0;

// Elements
const canvas = document.getElementById("drawing-canvas");
const colorBtns = document.querySelectorAll(".color-btn");
const toggleBtn = document.getElementById("toggle-drawing");
const undoBtn = document.getElementById("undo-btn");
const clearBtn = document.getElementById("clear-btn");
const countSpan = document.getElementById("circle-count");
const drawingText = document.getElementById("drawing-text");

// Init
document.addEventListener("DOMContentLoaded", () => {
  setupEvents();
  updateUI();
});

// Events
function setupEvents() {
  // Draw circle
  canvas.addEventListener("click", e => {
    if (!isDrawing) return;
    const { left, top } = canvas.getBoundingClientRect();
    const x = e.clientX - left, y = e.clientY - top;

    const circle = {
      id: `c-${++idCounter}`,
      x, y,
      r: Math.random() * 15 + 10,
      color: selectedColor
    };
    circles.push(circle);
    drawCircle(circle);
    updateUI();
  });

  // Color change
  colorBtns.forEach(btn => {
    btn.addEventListener("click", () => {
      colorBtns.forEach(b => b.classList.remove("active"));
      btn.classList.add("active");
      selectedColor = btn.dataset.color;
    });
  });

  // Toggle draw/select
  toggleBtn.addEventListener("click", () => {
    isDrawing = !isDrawing;
    updateUI();
  });

  // Undo
  undoBtn.addEventListener("click", () => {
    const last = circles.pop();
    if (last) document.getElementById(last.id)?.remove();
    updateUI();
  });

  // Clear
  clearBtn.addEventListener("click", () => {
    circles.forEach(c => document.getElementById(c.id)?.remove());
    circles = [];
    updateUI();
  });
}

// Draw SVG circle
function drawCircle({ id, x, y, r, color }) {
  const c = document.createElementNS("http://www.w3.org/2000/svg", "circle");
  c.setAttribute("id", id);
  c.setAttribute("cx", x);
  c.setAttribute("cy", y);
  c.setAttribute("r", r);
  c.setAttribute("fill", color);
  canvas.appendChild(c);
}

// UI Updates
function updateUI() {
  countSpan.textContent = circles.length;
  undoBtn.disabled = circles.length === 0;
  clearBtn.disabled = circles.length === 0;
  if (isDrawing) {
    drawingText.textContent = "Drawing";
    canvas.classList.add("drawing");
    canvas.classList.remove("selecting");
  } else {
    drawingText.textContent = "Selecting";
    canvas.classList.add("selecting");
    canvas.classList.remove("drawing");
  }
}
