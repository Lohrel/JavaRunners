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
    }

    @Override
    public void spawn(float startX, float startY, float startSpeed) {
        super.spawn(startX, startY, startSpeed);
        // posiciona o inimigo no chão
        this.y = 0;
    }
}

class FlyingEnemy extends Enemy {
    public FlyingEnemy(){
        this.width = 50;
        this.height = 30;
    }

    @Override
    public void spawn(float startX, float startY, float startSpeed) {
        super.spawn(startX, startY, startSpeed);
        // posiciona o inimigo voador na altura da cabeça do jogador, novamente, número abstrato.
        this.y = 100;
    }
}
