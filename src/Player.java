class Player
{
    public int life;
    public String state;
    public float height, width;
    public float x , y;
    public float velocitY;
    public boolean isOnGround;
    public float jumpForce;



    public Player(PlayerModel playerModel)
    {
        this.life = playerModel.getLife();
        this.state = playerModel.getState();
        this.height = playerModel.getHeight();
        this.width = playerModel.getWidth();
        this.x = playerModel.getX();
        this.y = playerModel.getY();
        this.velocitY = playerModel.getVelocitY(); // Atenção ao 'Y' maiúsculo que você colocou na variável!
        this.isOnGround = playerModel.isOnGround(); // Getters de boolean geralmente começam com 'is' em vez de 'get'
        this.jumpForce = playerModel.getJumpForce();
    }



    public void Crounch()
    {
        //Lógica de agachar
    }

    public void Jump()
    {
        if (isOnGround) {
            velocitY = jumpForce;
            isOnGround = false;
        }
    }
}
