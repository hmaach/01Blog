#!/bin/bash

if [ -f .env ]; then
    echo "Loading environment variables from .env..."
    while IFS= read -r line; do
        # Skip comments and empty lines
        if [[ "$line" =~ ^#.*$ ]] || [[ -z "$line" ]]; then
            continue
        fi
        export "$line"
    done < .env
else
    echo "No .env file found."
fi