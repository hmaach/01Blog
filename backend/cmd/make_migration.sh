#!/bin/bash

# exit if no name provided
if [ -z "$1" ]; then
  echo "❌ Please provide a migration name (use snake_case, e.g. create_users_table)."
  exit 1
fi

NAME=$1

VERSION=$(date +%s)
FILENAME="src/main/resources/db/migration/V${VERSION}__${NAME}.sql"

# create file if it doesn’t already exist
if [ -e "$FILENAME" ]; then
  echo "Migration already exists: $FILENAME"
  exit 1
fi

touch "$FILENAME"
echo "-- Migration: $NAME" > "$FILENAME"

echo "Created $FILENAME"
