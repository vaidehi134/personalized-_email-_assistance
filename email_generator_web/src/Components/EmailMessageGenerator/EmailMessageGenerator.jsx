import React, { useState } from "react";
import axios from "axios";
import styles from "./EmailMessageGenerator.module.css";
import { useNavigate } from "react-router-dom";

const EmailMessageGenerator = () => {
  const [purpose, setPurpose] = useState("");
  const [tone, setTone] = useState("");
  const [wordLimit, setWordLimit] = useState(0);
  const [to, setTo] = useState("");
  const [subject, setSubject] = useState("");
  const [scheduledTime, setScheduledTime] = useState("");
  const [generatedReply, setGeneratedReply] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const handleNextButton = () => {
    setPurpose("");
    setTone("");
    setWordLimit(0);
    setGeneratedReply("");
    setTo("");
    setSubject("");
    setScheduledTime("");
    setError("");
  };

  const handleGenerate = async () => {
    setLoading(true);
    setError("");

    try {
      console.log("Submitting with payload:", { purpose, tone, wordLimit });
      const response = await axios.post(
        "http://localhost:8080/api/email/generate/message",
        {
          purpose,
          tone,
          wordLimit,
        }
      );
      console.log("Response received:", response.data);
      setGeneratedReply(
        typeof response.data === "string"
          ? response.data
          : JSON.stringify(response.data)
      );
    } catch (error) {
      console.error("Error submitting request:", error);
      setError("Failed to generate email. Please try again");
    } finally {
      setLoading(false);
    }
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

  const handleBackButton = () => {
    navigate("/");
  };

  return (
    <div className={styles.container}>
      <h2 className={styles.title}>Generate Email</h2>

      <label>
        Purpose:
        <input
          type="text"
          name="purpose"
          value={purpose}
          onChange={(e) => setPurpose(e.target.value)}
          placeholder="Enter email purpose"
          className={styles.inputField}
        />
      </label>
      <br />

      <label>
        Word Limit:
        <input
          type="number"
          name="wordLimit"
          value={wordLimit}
          onChange={(e) => setWordLimit(e.target.value)}
          placeholder="Enter word limit"
          className={styles.inputField}
        />
      </label>
      <br />

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
          disabled={!purpose || loading}
        >
          {loading ? "Generating..." : "Generate Reply"}
        </button>
      </div>

      {error && <p className={styles.error}>{error}</p>}

      {generatedReply && (
        <div className={styles.outputContainer}>
          <h2>Generated Email:</h2>
          <textarea
            className={styles.textarea}
            rows="10"
            value={generatedReply || ""}
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
          <div className={styles.scheduleContainer}>
            <h2>Schedule Your Email</h2>
            <div className={styles.scheduleField}>
              <label>To:</label>
              <input
                type="email"
                value={to || ""}
                placeholder="Recipient Email"
                onChange={(e) => setTo(e.target.value)}
                className={styles.inputField}
              />
            </div>
            <div className={styles.scheduleField}>
              <label>Subject:</label>
              <input
                type="text"
                value={subject || ""}
                placeholder="Subject"
                onChange={(e) => setSubject(e.target.value)}
                className={styles.inputField}
              />
            </div>
            <div className={styles.scheduleField}>
              <label>Scheduled Time:</label>
              <input
                type="datetime-local"
                value={scheduledTime || ""}
                onChange={(e) => setScheduledTime(e.target.value)}
                className={styles.inputField}
              />
            </div>
            <button
              className={styles.button}
              onClick={handleSchedule}
              disabled={!to || !subject || !scheduledTime || loading}
            >
              {loading ? "Scheduling..." : "Schedule Email"}
            </button>
          </div>
        </div>
      )}

      <button className={styles.backButton} onClick={handleBackButton}>
        Back
      </button>
    </div>
  );
};

export default EmailMessageGenerator;
