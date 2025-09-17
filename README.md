# gRPC Mutual TLS (mTLS) with a Private CA 🛡️

This project shows how to secure a gRPC service with Mutual TLS (mTLS) using your own private Certificate Authority (CA). First, you create a CA (the “issuer of trust”) and then generate certificates for both the server and client, signed by that CA. The server gets its certificate and private key, and the client gets its own. Both also keep a copy of the CA’s certificate so they can verify each other. When the server and client connect, they exchange certificates: the client only trusts the server if its certificate is signed by the CA, and the server only accepts the client if its certificate is signed by the same CA. If both checks pass, communication is established securely—otherwise, it’s blocked.

## How It Works (Core Concept)

1. **Certificate Authority (CA):**  
   The CA acts as the trusted authority that issues and signs certificates for both the server and the client. Only certificates signed by this CA are considered valid.

2. **gRPC Server:**  
   The server holds a certificate issued by the CA (the *server certificate*). This allows the client to verify the server’s identity.

3. **gRPC Client:**  
   The client also holds a certificate issued by the same CA (the *client certificate*). This allows the server to verify the client’s identity.

4. **Mutual TLS Handshake (mTLS):**
    - The client connects to the server and receives the server’s certificate (`server.crt`).
    - The client verifies that this certificate was signed by the trusted CA (`ca.crt`).
    - The server then requests the client’s certificate (`client.crt`).
    - The server verifies that the client’s certificate was also signed by the trusted CA.
    - If both verifications succeed, a secure, mutually authenticated channel is established.

If either the client or server presents a certificate that is missing, invalid, or signed by an untrusted CA, the connection is immediately rejected.

---

## Prerequisites

Before you start, make sure you have these installed:

* **Java 21+** (JDK)
* **Gradle** 8.x
* **OpenSSL** (for generating certificates)

---

## 🚀 Let's Get It Running!

Follow these steps from the root directory of the project (`mtls/`).

### Step 1: Generate All Certificates and Keys

This is the most important step. We'll create a temporary folder, generate all the crypto files there, and then copy them where they need to go.

Run this entire block in your terminal from the project root:
````markdown
```bash
# Create a clean, temporary directory for our crypto files
mkdir -p temp-certs
cd temp-certs

# 1. Create our private Certificate Authority (CA)
echo "🔑 Generating CA Key and Certificate..."
openssl genpkey -algorithm RSA -out ca.key
openssl req -new -x509 -key ca.key -out ca.crt -days 365 -subj "/CN=My Private CA"

# 2. Create the Server's Key and Certificate (signed by our CA)
# The Common Name (CN) MUST be "localhost" for this demo to work!
echo "🖥️  Generating Server Key and Certificate for localhost..."
openssl genpkey -algorithm RSA -out server.key
openssl req -new -key server.key -out server.csr -subj "/CN=localhost"
openssl x509 -req -in server.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out server.crt -days 365

# 3. Create the Client's Key and Certificate (signed by our CA)
echo "👤 Generating Client Key and Certificate..."
openssl genpkey -algorithm RSA -out client.key
openssl req -new -key client.key -out client.csr -subj "/CN=grpc-client"
openssl x509 -req -in client.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out client.crt -days 365

# 4. Convert the private keys to the PKCS#8 format that Java prefers
echo "🔄 Converting keys to PKCS#8 format..."
openssl pkcs8 -topk8 -inform PEM -in server.key -outform PEM -nocrypt -out server.p8.key
openssl pkcs8 -topk8 -inform PEM -in client.key -outform PEM -nocrypt -out client.p8.key

echo "✅ All files generated successfully in the 'temp-certs' directory!"
cd ..
````

### Step 2: Copy the Files to the Projects

Now, copy the necessary files from `temp-certs` into the server and client resource folders.

```bash
# For the server
cp temp-certs/ca.crt temp-certs/server.crt temp-certs/server.p8.key modules/grpc-mtls-server/src/main/resources/certs/

# For the client
cp temp-certs/ca.crt temp-certs/client.crt temp-certs/client.p8.key modules/grpc-mtls-client/src/main/resources/certs/
```

### Step 3: Build the Entire Project

Let Gradle compile everything and prepare the applications.

```bash
./gradlew clean build
```

### Step 4: Run the Server and Client

You'll need **two separate terminal windows** for this.

**In Terminal 1 - Start the Server:**

```bash
./gradlew :modules:grpc-mtls-server:bootRun
```

Wait for the server to start. You'll see the log message:
`✅ gRPC mTLS Server Started on port 9090...`

**In Terminal 2 - Run the Client:**

```bash
./gradlew :modules:grpc-mtls-client:bootRun
```

-----

## What Success Looks Like ✅

If everything is configured correctly, your client terminal will spring to life and show the successful response from the server\!

```text
Sending request to server with name: Devappsys

--- RESPONSE FROM SERVER ---
Hello, Devappsys! Your request was secured with mTLS. 🛡️
----------------------------

BUILD SUCCESSFUL
```