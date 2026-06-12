import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
// Cache para armazenar as sprites na memória, sem precisar ficar puxando as imagens para cada instância nova objetos.
public class SpriteCache {
    private static final Map<String, Image> cache = new HashMap<>();

    public static Image getSprite(String imagePath) {
        if (cache.containsKey(imagePath)) {
            return cache.get(imagePath);
        }

        try {
            Image sprite = ImageIO.read(SpriteCache.class.getResourceAsStream(imagePath));
            cache.put(imagePath, sprite);
            return sprite;
        } catch (IOException | IllegalArgumentException | NullPointerException e) {
            System.err.println("Erro ao carregar a imagem: " + imagePath);
            return null;
        }
    }
}
