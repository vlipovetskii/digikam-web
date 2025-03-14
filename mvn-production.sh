# --- Build for Production https://vaadin.com/docs/latest/getting-started/tutorial/production-build
# 		./mvnw clean package -Pproduction
# --- Skipping Tests https://maven.apache.org/surefire/maven-surefire-plugin/examples/skipping-tests.html
# 		mvn install -DskipTests

# --- UI is broken when application is packaged using maven with production profile #20246 https://github.com/vaadin/flow/issues/20246#issuecomment-2715381204

./mvnw clean package -Pproduction -DskipTests

read -rp "OK. Press [Enter] key to exit. Or Ctrl-C to stay in terminal."

