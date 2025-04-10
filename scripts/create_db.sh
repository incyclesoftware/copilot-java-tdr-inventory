#!/bin/bash

# filepath: /home/tal/Documents/programming/Incycle/copilot-java-tdr-inventory/setup_database.sh

# Set the database directory and file
DB_DIR="database"
DB_FILE="$DB_DIR/inventory.db"

# Create the database directory if it doesn't exist
if [ ! -d "$DB_DIR" ]; then
    echo "Creating database directory..."
    mkdir "$DB_DIR"
fi

# Remove the database file if it already exists
if [ -f "$DB_FILE" ]; then
    echo "Removing existing database file..."
    rm "$DB_FILE"
fi

# Create a new SQLite database and run the SQL files
echo "Initializing database..."
sqlite3 "$DB_FILE" <<EOF
.read src/main/resources/database/schema.sql
.read src/main/resources/database/fill_db.sql
.read src/main/resources/database/insert_users.sql
EOF

echo "Database setup complete. File created at $DB_FILE"