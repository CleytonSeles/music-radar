🎵 MusicRadar

Uma aplicação completa para entusiastas de música que permite explorar artistas, álbuns e faixas com uma experiência integrada. Descubra, explore e acompanhe seu universo musical em um só lugar.

🚀 Começando

Essas instruções permitirão que você obtenha uma cópia do projeto em operação na sua máquina local para fins de desenvolvimento e teste.

Consulte Implantação para saber como implantar o projeto em ambiente de produção.

📋 Pré-requisitos

Para executar o projeto completo, você precisará instalar:

Backend
- Java 21
- PostgreSQL 13+
- Maven 3.8+

Frontend (Android)
- Android Studio Giraffe (2023.2.1) ou superior
- SDK Android mínimo: API 24 (Android 7.0)
- SDK Android alvo: API 34 (Android 14)

🔧 Instalação

Siga estes passos para configurar o ambiente de desenvolvimento:

Backend
1. Clone o repositório:
   ```sh
   git clone https://github.com/seu-usuario/music-radar.git
   cd music-radar/backend
   ```

2. Configure o banco de dados PostgreSQL no arquivo `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/musicradar
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Compile e inicie o backend:
   Para Linux/Mac:
   ```sh
   ./mvnw clean compile
   ./mvnw spring-boot:run
   ```

   Para Windows:
   ```sh
   .\mvnw.cmd clean compile
   .\mvnw.cmd spring-boot:run
   ```

   O servidor backend será iniciado em `http://localhost:8080`.

Frontend
1. Abra o Android Studio e importe o projeto:
   ```plaintext
   File > Open > selecione a pasta music-radar/android-app
   ```

2. Configure o endereço do backend:
   ```kotlin
   // Em app/src/main/java/com/musicradar/app/api/ApiConfig.kt
   object ApiConfig {
       // Para testes locais com emulador
       const val BASE_URL = "http://10.0.2.2:8080/api/"
       // Para testes com dispositivo físico (substitua pelo seu IP local)
       // const val BASE_URL = "http://192.168.0.XX:8080/api/"
   }
   ```

3. Execute o aplicativo:
   - Conecte um dispositivo Android via USB ou configure um emulador.
   - Clique no botão "Run" (▶️) no Android Studio.

⚙️ Executando os testes

O projeto inclui testes automatizados para garantir a qualidade do código.

🔩 Análise dos testes de ponta a ponta

Os testes de integração verificam se o fluxo de dados entre o frontend e backend funciona corretamente:

Para executar os testes de integração do backend:
```sh
.\mvnw.cmd test -Dtest=IntegrationTests
```

Para testar a API com Postman:
- Importe a coleção de testes em `/docs/MusicRadar.postman_collection.json`.

⌨️ E testes de estilo de codificação

Os testes de unidade verificam a lógica de negócio e validam a formatação do código:

Para testar componentes individuais do backend:
```sh
.\mvnw.cmd test -Dtest=ArtistServiceTest
```

Para testar o frontend:
- No Android Studio: botão direito na pasta de testes > Run Tests.

📦 Implantação
Para implantar em ambiente de produção:

1. Configure o perfil de produção no backend:
   ```properties
   spring.profiles.active=prod
   ```

2. Gere o APK de release para o Android:
   - No Android Studio: `Build > Generate Signed Bundle / APK`.

3. Configure um servidor de produção para hospedar o backend:
   ```sh
   java -jar backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
   ```

🛠️ Construído com
- Spring Boot - Framework para backend em Java/Kotlin
- Kotlin - Linguagem de programação moderna para JVM e Android
- Android SDK - Kit de desenvolvimento para aplicativos Android
- PostgreSQL - Sistema de gerenciamento de banco de dados
- Maven - Gerenciador de dependências
- Retrofit - Cliente HTTP para Android
- Glide - Biblioteca para carregamento eficiente de imagens


📄 Licença
Este projeto está sob a licença MIT - veja o arquivo `LICENSE.md` para detalhes.
