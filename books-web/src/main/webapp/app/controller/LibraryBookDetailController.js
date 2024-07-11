App.controller('LibraryBookDetailController', ['$scope', '$stateParams', 'Restangular', '$state', function($scope, $stateParams, Restangular, $state) {
    var bookId = $stateParams.bookId;
    console.log("Inside LibraryBookDetailController");
    var userId = "admin";

    // $scope.allowedGenres = ["Action", "Thriller", "Comedy", "Horror"];
    $scope.allowedGenres = ["Action", "Thriller", "Comedy", "Horror"].map(function(genre) {
        return { name: genre, checked: false };
    });

    Restangular.one('library/list').get().then(function(response) {
        var books = response.books;
        var bookDetails = books.filter(function(book) {
            return book.id === bookId;
        });

        if (bookDetails.length > 0) {
            $scope.book = bookDetails[0]; // Assuming IDs are unique, there should only be one match
            console.log("Fetched book details YAYAYAYA:", $scope.book);
        } else {
            console.error("Book not found");
            // Handle the case where the book is not found
        }
    }, function(error) {
        console.error("Error fetching books:", error);
    });

    $scope.newGenre = "";
    $scope.showGenreOptions = false;

    // $scope.addGenre = function() {
    //     if (!$scope.newGenre) {
    //         alert("Please enter a genre.");
    //         return;
    //     }
    //     console.log("Adding genre:", $scope.newGenre);
    //     if ($scope.allowedGenres.includes($scope.newGenre)) {
    //         Restangular.one('library/genre').customPUT($.param({
    //             libBookId: $scope.book.id,
    //             genreName: $scope.newGenre
    //         }), '', {}, {'Content-Type': 'application/x-www-form-urlencoded'}).then(function(response) {
    //             alert("Genre added successfully!");
    //             if (!$scope.book.genres) {
    //                 $scope.book.genres = [];
    //             }
    //             $scope.book.genres.push($scope.newGenre);
    //             $scope.newGenre = ""; // Reset the input field
    //         }, function(response) {
    //             console.error("Error adding genre:", response);
    //             alert("Error adding genre: " + response.data.message);
    //         });
    //     } else {
    //         alert("Genre not allowed. Please choose from: Action, Thriller, Comedy, Horror.");
    //     }
    // };    


    $scope.addGenre = function() {
        var selectedGenres = $scope.allowedGenres.filter(function(genre) {
            return genre.checked;
        }).map(function(genre) {
            return genre.name;
        });

        if (selectedGenres.length === 0) {
            alert("Please select at least one genre.");
            return;
        }

        selectedGenres.forEach(function(genre) {
            Restangular.one('library/genre').customPUT($.param({
                libBookId: $scope.book.id,
                genreName: genre
            }), '', {}, {'Content-Type': 'application/x-www-form-urlencoded'}).then(function(response) {
                if (!$scope.book.genres.includes(genre)) {
                    $scope.book.genres.push(genre);
                }
                // Reset checkboxes after successful addition
                $scope.allowedGenres.forEach(function(genre) {
                    genre.checked = false;
                });
                alert("Genres added successfully!");
            }, function(response) {
                console.error("Error adding genre:", response);
                alert("Error adding genre: " + response.data.message);
            });
        });
    };

    $scope.bookRating = 5; 

    $scope.addRating = function() {
        Restangular.one('library/rating').customPUT($.param({
            libBookId: $scope.book.id,
            userId: $scope.book.currently_logged_userid,
            rating: $scope.bookRating
        }), '', {}, {'Content-Type': 'application/x-www-form-urlencoded'}).then(function(response) {
            alert("Rating added successfully!");
        }, function(response) {
            console.error("Error adding rating:", response);
            alert("Error adding rating: " + response.data.message);
        });
    };
}]);