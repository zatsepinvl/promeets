app.controller("cardsCtrl", function ($scope,
                                      appConst,
                                      Entity,
                                      $state,
                                      UserService,
                                      MeetService,
                                      CardEditDialogService,
                                      ConfirmDialog,
                                      EventHandler) {
    $scope.cards = MeetService.getCards();

    $scope.createCard = function (event) {
        var card = {
            user: $scope.user,
            meet: $scope.meet,
            time: moment().utc().valueOf()
        };
        Entity.save({entity: "cards"}, card,
            function (data) {
                card = data;
                console.log(card.image);
                CardEditDialogService.show(card, event,
                    function (card) {
                        Entity.update({entity: "cards", id: card.cardId}, card);
                        $scope.cards.push(card);
                    },
                    function () {
                        Entity.delete({entity: "cards", id: card.cardId});
                    });
            }
        );
    };

    $scope.editCard = function (card, event) {
        CardEditDialogService.show(card, event,
            function (newCard) {
                $scope.cards[$scope.cards.indexOf(card)] = newCard;
                Entity.update({entity: "cards", id: newCard.cardId}, newCard);
            });
    };

    $scope.deleteCard = function (card, event) {
        ConfirmDialog.show('Delete card?', 'Delete', 'Cancel', event,
            function () {
                //EventHandler.load('Deleting card');
                Entity.remove({entity: "cards", id: card.cardId}, function () {
                        EventHandler.message('Card has been deleted');
                        $scope.cards.splice($scope.cards.indexOf(card), 1);
                    },
                    function (error) {
                        EventHandler.message('Something went wrong. Try again later.');
                    });
            });
    }
});