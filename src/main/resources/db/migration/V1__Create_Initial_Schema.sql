-- Create members table
CREATE TABLE members (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    membership_start_date DATE NOT NULL,
    membership_duration INT NOT NULL,
    PRIMARY KEY (id)
);

-- Create tournaments table
CREATE TABLE tournaments (
    id BIGINT NOT NULL AUTO_INCREMENT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    location VARCHAR(255) NOT NULL,
    entry_fee DECIMAL(10,2) NOT NULL,
    cash_prize DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id)
);

-- Create join table for many-to-many relationship
CREATE TABLE tournament_participants (
    tournament_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    PRIMARY KEY (tournament_id, member_id),
    FOREIGN KEY (tournament_id) REFERENCES tournaments(id),
    FOREIGN KEY (member_id) REFERENCES members(id)
);

-- Add indexes for better performance
CREATE INDEX idx_members_email ON members(email);
CREATE INDEX idx_tournaments_dates ON tournaments(start_date, end_date);