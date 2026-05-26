class PlayerModel
{
    private int life;
    private String state;
    private float height = 50, width = 50;
    private float x , y;
    private float velocitY;
    private boolean isOnGround;
    private float jumpForce;



    public void setPlayerX(float x)
    {
        this.x = x;
    }


    public void setPlayerY(float y)
    {
        this.y = y;
    }

    public void setVelocitY(float velocityY) {
        this.velocitY = velocityY;
    }

    public void setOnGround(boolean onGround) {
        isOnGround = onGround;
    }

    public boolean isOnGround() {
        return isOnGround;
    }

    public void setJumpForce(float jumpForce) {
        this.jumpForce = jumpForce;
    }

    public float getVelocitY() {
        return velocitY;
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public int getLife() {
        return life;
    }

    public String getState() {
        return state;
    }

    public float getJumpForce() {
        return jumpForce;
    }
}