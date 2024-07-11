'use strict';

/**
 * Signup controller.
 */
App.controller('Audio', function($scope, $q, $state, $timeout, Restangular) {
  $scope.searchResults = [];
  $scope.selectedResult = {}; 

  $scope.searchContent = function() {
    console.log('Search content called');$timeout
    if ($scope.selectedServiceProvider && $scope.selectedContentType && $scope.searchQuery) {
        if ($scope.selectedContentType === 'audiobooks') {
          Restangular.one('audio/audiobook/list').get(
              {
                  q: $scope.searchQuery, 
                  provider: $scope.selectedServiceProvider,
                  contentType: $scope.selectedContentType
              }).then(function(response) {
              $scope.searchResults = response.audioBooks;

              console.log("Results : " + $scope.searchResults);
          }).catch(function(response) {
              console.log("Error : " + response);
          });
        } else if ($scope.selectedContentType === 'podcasts') {
           Restangular.one('audio/podcast/list').get(
               {
                   q: $scope.searchQuery, 
                   provider: $scope.selectedServiceProvider,
                   contentType: $scope.selectedContentType
               }).then(function(response) {
               $scope.searchResults = response.podcasts;

               console.log("Results : " + $scope.searchResults);
           }).catch(function(response) {
               console.log("Error : " + response);
           });
        }
    }

    $scope.addToAudioBookDB = function(result) {  
        console.log("Adding to AudioContent database");

        if($scope.selectedContentType === 'audiobooks') {
            console.log("Adding to AudioBook database");
            console.log("Selected result:", result);
        
            // Check if the book already exists in the database
            var promise = Restangular.one('audio/audiobook').get({ id: result.id });
            promise.then(function(response) {
                // Book already exists, do nothing
                console.log("Book already exists in database");
                console.log("result.id: ", result.id);
                $state.transitionTo('audiobookcontent', { id: result.id });
            }).catch(function(error) {
                // Book doesn't exist, add it to the database
                console.log("Book doesn't exist in database, adding it now");
                
                // Extract required fields from the result object
                var postData = {
                    id: result.id,
                    title: result.title,
                    author: result.author,
                    description: result.description
                };
        
                var addPromise = Restangular.one('audio/audiobook').post(null, postData);
                
                addPromise.then(function(response) {
                    console.log("Added to Audiobook database:", response);

                }).catch(function(error) {
                    console.error("Error during adding to database:", error);
                    // Handle error cases here
                });

                $q.all([addPromise]).then(function(){
                    $state.transitionTo('audiobookcontent', { id: result.id });
                })
            });
        }else if ($scope.selectedContentType === 'podcasts') {
            console.log("Adding to Podcast database");
            console.log("Selected result:", result);
            console.log("result.id: ", result.id);
            console.log("result.title: ", result.title);
            console.log("result.artist: ", result.artist);
            console.log("result.viewUrl: ", result.viewUrl);
        
            // Check if the book already exists in the database
            var promise = Restangular.one('audio/podcast').get({ id: result.id });
            promise.then(function(response) {

                // Book already exists, do nothing
                console.log("Book already exists in database");
                $state.transitionTo('podcastcontent', { id: result.id });
            }).catch(function(error) {
                
                // Book doesn't exist, add it to the database
                console.log("Book doesn't exist in database, adding it now");
                
                // Extract required fields from the result object
                var postData = {
                    id: result.id,
                    title: result.title,
                    artist: result.artist,
                    viewUrl: result.viewUrl
                };

                console.log("Post Data: ",result.artist,result.viewUrl);

                var addPromise = Restangular.one('audio/podcast').post(null, postData);

                addPromise.then(function(response) {
                    console.log("Added to Podcast database:", response);
                }).catch(function(error) {
                    console.error("Error during adding to database:", error);
                });

                $q.all([addPromise]).then(function(){
                    $state.transitionTo('podcastcontent', { id: result.id });
                })

            });
        }
        else {
            console.error("Invalid content type");
        }
    };
    
    $scope.viewDetails = function(result) {
        $scope.selectedResult = result; // Store the clicked result in the selectedResult model
        console.log("Selected result:", $scope.selectedResult); // You can remove this line after testing
        $scope.addToAudioContentDB(result); // Call the function to add to the AudioContent database
    };
    
};
});
