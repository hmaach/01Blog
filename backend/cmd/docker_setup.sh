#!/bin/bash

# === Try to run docker compose ===
OUTPUT=$(docker compose up -d 2>&1)

if echo "$OUTPUT" | grep -q "Cannot connect to the Docker daemon"; then
    echo "Docker daemon not running or not installed. Attempting to install Docker..."

    if [ -x "cmd/install_docker.sh" ]; then
        ./cmd/install_docker.sh
    else
        echo "Error: Installer script cmd/install_docker.sh not found or not executable."
        exit 1
    fi

    echo "Retrying docker compose up -d..."
    docker compose up -d
else
    echo "$OUTPUT"
fi
