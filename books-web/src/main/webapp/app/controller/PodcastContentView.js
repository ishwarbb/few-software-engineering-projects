'use strict';

/**
 * Controller for PodcastContentView.
 */
App.controller('PodcastContentView', function($scope, $stateParams, Restangular) {
    // Extract the book ID from the state parameters
    var podcastId = $stateParams.id;

    console.log("Podcast-ID: ",podcastId);

    // Make API call to fetch book details by ID
    Restangular.one('audio/podcast').get({ id: podcastId.toString() }).then(function(response) {
        // Assign the fetched book details to $scope.selectedResult
        $scope.selectedResult = response;

        // Optionally, you can access other properties of the selectedResult and perform additional operations
        
        console.log("Book details fetched successfully:", $scope.selectedResult);
    }).catch(function(error) {
        console.error("Error fetching book details:", error);
    });

    // Initialize isFavourite to false
    $scope.isFavourite = false;

    // do get request to @Path("/audio/audiobook/favorites")
    Restangular.one('audio/podcast/favorites').get().then(function(response) {
        console.log("Favourites Response:", response);
        $scope.podcastFavorites = response.userPodcasts;

        for (var i = 0; i < $scope.podcastFavorites.length; i++) {
            if ($scope.podcastFavorites[i].podcastId === podcastId) {
                $scope.isFavourite = true;
                console.log("Book is a favourite");
                break;
            }
        }
    }).catch(function(error) {
        console.error("Error during getting Favourites:", error);
    });

    $scope.toggleFavourite = function() {
        if ($scope.isFavourite) {
            $scope.addFavourite($scope.selectedResult);
        } else {
            $scope.deleteFavourite($scope.selectedResult);
        }
    };
    
    
    $scope.deleteFavourite = function(result) {
        // Logic to remove result from favourites
        console.log("Removing from favourites");

        var promise = Restangular.one('audio/podcast/favorites').remove({ id: result.id });
        promise.then(function(response) {
            console.log("Removed from favourites:", response);
        }   ).catch(function(error) {
            console.error("Error during removing from favourites:", error);
        });


        console.log("Removed from favourites:", result);
    };


    $scope.addFavourite = function(result) {  
        console.log("Adding to favourites");
        console.log("Selected result:", result); // You can remove this line after testing
        console.log("Attempting to add to favourites");
    // Extract required fields from the result object
        var postData = {
            id: result.id,
            title: result.title,
            artist: result.artist,
            viewUrl: result.viewUrl
        };

        var promise = Restangular.one('audio/podcast/favorites').post(null, postData);
        promise.then(function(response) {
            console.log("Added to favourites:", response);
            // Handle success response here if needed
        }).catch(function(error) {
            console.error("Error during adding to favourites:", error);
            // Handle error cases here
        });
    }

});

