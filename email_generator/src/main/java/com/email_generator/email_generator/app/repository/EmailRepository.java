package com.email_generator.email_generator.app.repository;

import com.email_generator.email_generator.app.model.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailModel, Long> {
}
