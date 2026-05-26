import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

abstract class Enemy {
    protected int Health;
    protected float Speed;
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
        if (active && sprite != null) {
            g.drawImage(sprite, (int) x, (int) y, (int) width, (int) height, null);
        }
    }

    public void spawn(float startX, float startY, float startSpeed) {
        this.x = startX;
        this.y = startY;
        this.Speed = startSpeed;
        this.active = true;
    }
    // update serve para mover os inimigos para esquerda até sumirem do campo de visão, o número 100 é abstrato e não reflete o valor real, vamos descobrir isso quando definirmos a interface gráfica.
    public void update() {
        if (active) {
            this.x -= Speed;
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
        this.width = 30;
        this.height = 50;
        loadSprite("/sprites/ground_enemy.png");
    }

    @Override
    public void spawn(float startX, float startY, float startSpeed) {
        super.spawn(startX, startY, startSpeed);
        // posiciona o inimigo no chão
        this.y = 0;
    }
}

class FlyingEnemy extends Enemy {
    private float startYPosition;
    private float oscillationTimer;
    public FlyingEnemy(){
        this.width = 50;
        this.height = 30;
        loadSprite("/sprites/flying_enemy.png");
    }

    @Override
    public void spawn(float startX, float startY, float startSpeed) {
        super.spawn(startX, startY, startSpeed);
        // posiciona o inimigo voador na altura da cabeça do jogador, novamente, número abstrato.
        this.startYPosition = 100;
        // Calcula o movimento de senoide do inimigo voador (voando pra cima e para baixo)
        this.oscillationTimer = 0;
    }

    @Override
    public void update() {
        super.update();

        if (active) {
            oscillationTimer += 0.1f;

            this.y = startYPosition + (float) Math.sin(oscillationTimer) * 30;
        }
    }
}
