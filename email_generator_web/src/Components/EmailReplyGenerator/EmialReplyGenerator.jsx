import React, { useState } from "react";
import axios from "axios";
import styles from "./EmailReplyGenerator.module.css";
import { useNavigate } from "react-router-dom";

const EmailReplyGenerator = () => {
  const [emailContent, setEmailContent] = useState("");
  const [tone, setTone] = useState(" ");
  const [generatedReply, setGeneratedReply] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [to, setTo] = useState("");
  const [subject, setSubject] = useState("");
  const [scheduledTime, setScheduledTime] = useState("");

  const handleNextButton = () => {
    setEmailContent("");
    setTone(" ");
    setGeneratedReply("");
    setTo("");
    setSubject("");
    setScheduledTime("");
    setError("");
  };

  const navigate = useNavigate();
  const handleGenerate = async () => {
    setLoading(true);
    setError("");

    try {
      console.log("Generating with payload:", { emailContent, tone });
      const response = await axios.post(
        "http://localhost:8080/api/email/generate",
        {
          emailContent,
          tone,
        }
      );
      console.log("Generated response received:", response.data);
      setGeneratedReply(
        typeof response.data === "string"
          ? response.data
          : JSON.stringify(response.data)
      );
    } catch (error) {
      console.error("Error generating email reply:", error);
      setError("Failed to generate email reply. Please try again");
    } finally {
      setLoading(false);
    }
  };

  const handleBackButton = () => {
    navigate("/");
  };

  const handleSchedule = async () => {
    setLoading(true);
    setError("");

    try {
      console.log("Scheduling with payload:", {
        to,
        subject,
        generatedReply,
        scheduledTime,
      });
      const response = await axios.post(
        "http://localhost:8080/api/email/schedule",
        {
          to,
          subject,
          text: generatedReply,
          scheduledTime,
        }
      );
      console.log("Scheduling response received:", response.data);
      alert("Email scheduled successfully!");
    } catch (error) {
      console.error("Error scheduling email:", error);
      setError("Failed to schedule email. Please try again");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.container}>
      <h2 className={styles.title}>Generate Your Reply</h2>

      <textarea
        className={styles.tarea}
        value={emailContent || " "}
        placeholder="Original Email Content"
        onChange={(e) => setEmailContent(e.target.value)}
        rows="5"
        cols="30"
      />

      <div className={styles.flexRow}>
        <select
          className={styles.select}
          value={tone || ""}
          onChange={(e) => setTone(e.target.value)}
        >
          <option value="">None</option>
          <option value="professional">Professional</option>
          <option value="casual">Casual</option>
          <option value="friendly">Friendly</option>
        </select>

        <button
          className={styles.button}
          onClick={handleGenerate}
          disabled={!emailContent || loading}
        >
          {loading ? "Generating..." : "Generate Reply"}
        </button>
      </div>
      {error && <p className={styles.error}>{error}</p>}

      {generatedReply && (
        <div className={styles.outputContainer}>
          <h2>Generated Reply</h2>
          <textarea
            className={styles.textarea}
            value={generatedReply || ""}
            readOnly
          />
          <div className={styles.buttonGroup}>
            <button
              className={styles.button}
              onClick={() => navigator.clipboard.writeText(generatedReply)}
            >
              Copy to Clipboard
            </button>
            <button className={styles.button} onClick={handleNextButton}>
              Next
            </button>
          </div>
        </div>
      )}

      <div className={styles.scheduleContainer}>
        <h2 className={styles.title}>Schedule Your Email</h2>

        <input
          className={styles.inputReply}
          type="email"
          value={to || ""}
          placeholder="Recipient Email"
          onChange={(e) => setTo(e.target.value)}
        />

        <input
          className={styles.inputReply}
          type="text"
          value={subject || ""}
          placeholder="Subject"
          onChange={(e) => setSubject(e.target.value)}
        />

        <input
          className={styles.inputReply}
          type="datetime-local"
          value={scheduledTime || ""}
          onChange={(e) => setScheduledTime(e.target.value)}
        />

        <button
          className={styles.button}
          onClick={handleSchedule}
          disabled={!to || !subject || !scheduledTime || loading}
        >
          {loading ? "Scheduling..." : "Schedule Email"}
        </button>

        <button className={styles.backButton} onClick={handleBackButton}>
          Back
        </button>
      </div>
    </div>
  );
};

export default EmailReplyGenerator;
