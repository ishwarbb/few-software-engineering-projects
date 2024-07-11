// 'use strict';

// App.controller('Favourites', function($scope, $state, Restangular) {

//     $scope.audioBookFavourites = [];
//     $scope.podcastFavourites = [];

//     console.log("Favourites called");

//     Restangular.one('audio/audiobook/favorites').get().then(function(response) {
//         console.log("Favourites Response:", response);
//         $scope.audioBookFavourites = response.audioBooks;
//         console.log("AudioBook Favourites:", $scope.audioBookFavourites);
//     }).catch(function(error) {
//         console.error("Error during getting Favourites:", error);
//         // Handle error cases here
//     });

//     // Restangular.one('audio/podcast/favorites').get().then(function(response) {
//     //     console.log("Favourites Response:", response);
//     //     $scope.podcastFavourites = response;
//     // }).catch(function(error) {
//     //     console.error("Error during getting Favourites:", error);
//     //     // Handle error cases here
//     // });

//     $scope.favourites = function() {
//         console.log("Favourites called");
//         // Get request to fetch the favourites
//         Restangular.one('audio/audiobook/favorites').get().then(function(response) {
//             console.log("Favourites Response:", response);
//         }).catch(function(error) {
//             console.error("Error during getting Favourites:", error);
//             // Handle error cases here
//         });
//     }
// });

'use strict';

App.controller('Favourites', function($scope, $state, Restangular) {
    $scope.audioBookFavourites = [];
    $scope.podcastFavourites = [];
    $scope.activeFavouritesType = 'audiobook'; // Default selected option

    $scope.fetchFavourites = function(type) {
        console.log("Fetching " + type + " favourites");
        var endpoint = type === 'podcast' ? 'audio/podcast/favorites' : 'audio/audiobook/favorites';

        Restangular.one(endpoint).get().then(function(response) {
            console.log(type.charAt(0).toUpperCase() + type.slice(1) + " Favourites Response:", response);
            if (type === 'podcast') {
                $scope.podcastFavourites = response.podcasts;
            } else {
                $scope.audioBookFavourites = response.audioBooks;
            }
        }).catch(function(error) {
            console.error("Error during getting " + type + " Favourites:", error);
        });
    };

    // Fetch audio book favourites by default
    $scope.fetchFavourites('audiobook');

    // Toggle favourites between audio books and podcasts
    $scope.toggleFavourites = function(type) {
        if (type !== $scope.activeFavouritesType) {
            $scope.activeFavouritesType = type;
            $scope.fetchFavourites(type);
        }
    };

    $scope.viewFavourite = function(result) {
        console.log("Viewing favourite");
        if ($scope.activeFavouritesType === 'audiobook') {
            $state.transitionTo('audiobookcontent', { id: result.id });
        } else {
            $state.transitionTo('podcastcontent', { id: result.id });
        }
    }
});
