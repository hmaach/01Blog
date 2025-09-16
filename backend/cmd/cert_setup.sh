#!/bin/bash
set -e

CERT_DIR="./certs"
CERT_FILE="$CERT_DIR/blog.p12"

if [ -f .env ]; then
  source .env
fi

CERT_PASSWORD=${CERT_PASSWORD:-default}
CERT_ALIAS=${CERT_ALIAS:-blog}
CERT_DNAME=${CERT_DNAME:-"CN=localhost, OU=Dev, O=blog, L=Oujda, ST=Oriental, C=MA"}

mkdir -p "$CERT_DIR"

# Generate cert only if missing
if [ ! -f "$CERT_FILE" ]; then
  echo "Generating self-signed SSL certificate..."
  keytool -genkeypair \
    -alias "$CERT_ALIAS" \
    -keyalg RSA \
    -keysize 2048 \
    -storetype PKCS12 \
    -keystore "$CERT_FILE" \
    -storepass "$CERT_PASSWORD" \
    -validity 365 \
    -dname "$CERT_DNAME"

  echo "Certificate generated at $CERT_FILE"
else
  echo "Certificate already exists: $CERT_FILE"
fi
