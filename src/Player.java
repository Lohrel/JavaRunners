class Player {

    public int life;
    public String state;

    public float height;
    public float width;

    public float x;
    public float y;

    public float velocitY;

    public boolean isOnGround;

    public float jumpForce;

    public Player(PlayerModel playerModel) {

        this.life = playerModel.getLife();
        this.state = playerModel.getState();

        this.height = playerModel.getHeight();
        this.width = playerModel.getWidth();

        this.x = playerModel.getX();
        this.y = playerModel.getY();

        this.velocitY = playerModel.getVelocitY();

        this.isOnGround = playerModel.isOnGround();

        this.jumpForce = playerModel.getJumpForce();

        // caso venha 0 do model
        if (this.jumpForce <= 0) {
            this.jumpForce = 12f;
        }
    }

    public void Crounch() {

    }

    public void Jump() {

        if (isOnGround) {

            velocitY = -jumpForce;

            isOnGround = false;
        }
    }
}
