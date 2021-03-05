keytool -genkeypair -keystore keystore/$1 -keyalg RSA -storepass password -alias key
keytool -certreq -keystore keystore/$1 -file key.csr -storepass password -alias key
openssl x509 -req -in key.csr -CA myCA.pem -CAkey myCA.key -CAcreateserial -out key.pem
keytool -importcert -file myCA.pem -keystore keystore/$1 -storepass password -alias CA
keytool -importcert -file key.pem -keystore keystore/$1  -storepass password -alias key