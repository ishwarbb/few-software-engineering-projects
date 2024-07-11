'use strict';

App.controller('LibraryAddBook', ['$scope', '$state', 'Restangular', function($scope, $state, Restangular) {
  console.log("LibraryAddBook controller");
  $scope.isbn = "";

  $scope.addBook = function() {
    Restangular.all('library').customPUT({isbn: $scope.isbn}, '', {}, {'Content-Type': 'application/x-www-form-urlencoded'}).then(function(response) {
      console.log("Book successfully added:", response);
      alert("Book added successfully! ID: " + response.id);
      $scope.isbn = ""; // Clearing the form
      $state.go('library'); // Redirecting to library main page
    }, function(response) {
      console.error("Error adding book:", response);
      alert("Please enter a valid ISBN. Error adding book: " + response.data.message);
    });
  };

  $scope.cancel = function() {
    $state.go('library'); // Redirect to library main page
  };
}]);