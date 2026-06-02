import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

abstract class Enemy {
    protected int health;
    protected float speed;
    // x e y definem a posição da sprite dos inimigos.
    protected float x;
    protected float y;
    // width e height vão definir o tamanho da hitbox
    protected float width;
    protected float height;
    protected boolean active;
    //Armazena a sprite
    protected Image sprite;

    public void loadSprite(String imagePath) {
        try {
            this.sprite = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Erro ao carregar a imagem: " + imagePath);
        }
    }

    public void draw(Graphics g) {
        if (active) {
            if (sprite != null) {
                g.drawImage(sprite, (int) x, (int) y, (int) width, (int) height, null);
            } else {
                // Fallback caso não tenha sprite
                if (this instanceof GroundEnemy) g.setColor(Color.RED);
                else if (this instanceof FlyingEnemy) g.setColor(Color.MAGENTA);
                g.fillRect((int) x, (int) y, (int) width, (int) height);
            }
        }
    }

    public void spawn(float startX, float startY, float startSpeed) {
        this.x = startX;
        this.y = startY;
        this.speed = startSpeed;
        this.active = true;
    }
    // update serve para mover os inimigos para esquerda até sumirem do campo de visão
    public void update() {
        if (active) {
            this.x -= speed;
            if (this.x < -100) {
                this.active = false;
            }
        }
    }
    //Retorna o tamanho da hitbox para verificar colisão
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    public boolean isActive() {
        return active;
    }
}

interface enemySpawner {
    void spawnEnemy();
}

class GroundEnemy extends Enemy {
    public GroundEnemy(){
        this.width = 40;
        this.height = 40;
        loadSprite("/sprites/ground_enemy.png");
    }

    @Override
    public void spawn(float startX, float startY, float startSpeed) {
        super.spawn(startX, startY, startSpeed);
        // O startY aqui será o topo do chão
        this.y = startY - this.height;
    }
}

class FlyingEnemy extends Enemy {
    private float startYPosition;
    private float oscillationTimer;
    public FlyingEnemy(){
        this.width = 40;
        this.height = 30;
        loadSprite("/sprites/flying_enemy.png");
    }

    @Override
    public void spawn(float startX, float startY, float startSpeed) {
        super.spawn(startX, startY, startSpeed);
        this.startYPosition = startY;
        this.oscillationTimer = 0;
    }

    @Override
    public void update() {
        super.update();

        if (active) {
            oscillationTimer += 0.05f;
            this.y = startYPosition + (float) Math.sin(oscillationTimer) * 50;
        }
    }
}
