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

    $scope.createCard = function () {
        var card = {
            user: $scope.user,
            meet: $scope.meet,
            time: moment().utc().valueOf(),
            isNew: true
        };
        $scope.newCard = card;
        Entity.save({entity: "cards"}, card,
            function (data) {
                clone(data, card);
            }
        );
    };

    $scope.editCard = function (card) {
        card.editing = true;
    };

    $scope.saveCard = function (card) {
        card.editing = false;
        if (card.isNew) {
            $scope.cards.push(card);
            card.isNew = false;
            $scope.newCard = undefined;
        }
        Entity.update({entity: "cards", id: card.cardId}, card);
    };

    $scope.deleteCard = function (card, event) {
        ConfirmDialog.show('Delete card?', 'Delete', 'Cancel', event,
            function () {
                //EventHandler.load('Deleting card');
                Entity.remove({entity: "cards", id: card.cardId}, function () {
                        if (!card.isNew) {
                            EventHandler.message('Card has been deleted');
                            $scope.cards.splice($scope.cards.indexOf(card), 1);
                        }
                        $scope.newCard = undefined;
                    },
                    function (error) {
                        if (!card.isNew) {
                            EventHandler.message('Something went wrong. Try again later.');
                        }
                        $scope.newCard = undefined;
                    });
            });
    };
});