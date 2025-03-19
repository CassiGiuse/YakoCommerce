package utils;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;

public class EnvLoader {
  private final Dotenv dotenv;

  public EnvLoader(String envDirPath) {
    try {
      this.dotenv = Dotenv.configure()
          .directory(envDirPath)
          .load();
    } catch (DotenvException e) {
      throw new RuntimeException("Impossibile caricare il file .env dalla directory: " + envDirPath, e);
    }
  }

  public String get(String key) {
    return dotenv.get(key);
  }

  public String get(String key, String defaultValue) {
    String value = dotenv.get(key);
    return value != null ? value : defaultValue;
  }
}