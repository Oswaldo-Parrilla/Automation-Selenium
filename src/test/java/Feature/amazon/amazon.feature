Feature: Buy Pc Gamer

  @SorianaDespensa
  Scenario: I need to buy in Amazon
    Given I enter the Amazon page
    When I open browser google
    And I search "Amazon.com.mx: Precios bajos - Envío rápido - Millones de productos"
    And I log in to the portal
    And I search the following products in the soriana page:
      | motherboard am5    |
      | memoria ram ddr5     |
