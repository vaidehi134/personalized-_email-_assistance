// import { Route, Router, Routes } from "react-router-dom";
// import "./App.css";
// import EmailMessageGenerator from "./Components/EmailMessageGenerator/EmailMessageGenerator";
// import Home from "./Components/Home/Home";
// import EmailReplyGenerator from "./Components/EmailReplyGenerator/EmialReplyGenerator";

// function App() {
//   return (
//     <div className="app">
//       <Router>
//         <Routes>
//           <Route path="/" element={<Home />} />
//           <Route
//             path="/email-reply-generator"
//             element={<EmailReplyGenerator />}
//           />
//           <Route
//             path="/email-message-generator"
//             element={<EmailMessageGenerator />}
//           />
//         </Routes>
//       </Router>
//     </div>
//   );
// }

// export default App;

import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import "./App.css";
import EmailMessageGenerator from "./Components/EmailMessageGenerator/EmailMessageGenerator";
import Home from "./Components/Home/Home";
import EmailReplyGenerator from "./Components/EmailReplyGenerator/EmialReplyGenerator";

function App() {
  return (
    <div className="app">
      <Router>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route
            path="/email-reply-generator"
            element={<EmailReplyGenerator />}
          />
          <Route
            path="/email-message-generator"
            element={<EmailMessageGenerator />}
          />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
