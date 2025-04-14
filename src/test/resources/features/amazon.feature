Feature: Buy Pc Gamer

  @SorianaDespensa
  Scenario: I need to buy in Amazon
    Given I enter the Amazon page
    When I open browser google
    And I search "Amazon.com.mx: Precios bajos - Envío rápido - Millones de productos"
    And I log in to the portal
    And I search the following products in the soriana page:
      | motherboard am5                                               |
      | TEAMGROUP T-Force Delta RGB DDR5 32GB Kit (2x16GB) 6000MHz    |
      | Amd CPU RYZEN 7 7800X3D                                       |
      | Lian Li Hydroshift 360 AIO - Pre-Installed 3 x 28MM Fans      |
      | y60 hyte                                                      |
      | GIGABYTE Tarjeta de Video Aero GeForce RTX4060TI OC 8GB White |
      | Teclado asus white inalambrico                                |
      | Audifonos asus white                                          |
      | Mouse razer white                                             |
      | monitor asus 4k white                                         |
      | control ps5 white                                             |
    And I proceed to payment
    And I add credit card pay
