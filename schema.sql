CREATE TABLE IF NOT EXISTS currency_rates (
                                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                              currency VARCHAR(10) NOT NULL,
                                              rate DECIMAL(19, 6) NOT NULL,
                                              currency_type VARCHAR(20) NOT NULL,
                                              created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
