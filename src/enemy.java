abstract class Enemy {
    protected int Health;
    protected float Speed;
    protected float x;
    protected float y;
    protected boolean active;

    public void spawn(float startX, float startY, float startSpeed) {
        this.x = startX;
        this.y = startY;
        this.Speed = startSpeed;
        this.active = true
    }

    public boolean isActive() {
        return active;
    }

}

interface enemySpawner () {

}
