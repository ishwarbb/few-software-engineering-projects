'use strict';

App.controller('LibraryController', ['$scope', 'Restangular', '$state', function($scope, Restangular, $state) {
    $scope.books = []; // Initialize the books array

    console.log("Inside LibraryController");
    
    $scope.showFilters = false; 
    $scope.showRanking = false;
    $scope.librarySort = 0; // Default to no sorting

    $scope.toggleFilters = function() {
        console.log("Toggling filters");
        $scope.showFilters = !$scope.showFilters;
        console.log("Show filters:", $scope.showFilters);
    };

    $scope.toggleRanking = function() {
        console.log("Toggling ranking");
        $scope.showRanking = !$scope.showRanking;
        console.log("Show ranking:", $scope.showRanking);
    };

    $scope.filters = {
        authors: '',
        availableGenres: [
            { name: 'Horror', checked: false },
            { name: 'Thriller', checked: false },
            { name: 'Comedy', checked: false },
            { name: 'Action', checked: false }
        ],
        rating: ''
    };

    $scope.rankingOptions = [
        { label: 'Average Rating', value: 'avg_rating' },
        { label: 'Number of Ratings', value: 'num_ratings' }
    ];

    // Function to fetch all books with filters
    $scope.fetchBooks = function() {
        var authorNames = $scope.filters.authors.length ? $scope.filters.authors.split(',').map(function(author) { return author.trim(); }) : [];
        var selectedGenres = $scope.filters.availableGenres.filter(function(genre) { return genre.checked; }).map(function(genre) { return genre.name; });
        var queryParams = {
            filter_authors: authorNames.join(','),
            filter_genres: selectedGenres.join(','),
            filter_min_rating: $scope.filters.rating,
            library_sort: $scope.librarySort
        };

        // If sorting by ratings, apply the limit to get top 10 books
        if ($scope.librarySort === 1 || $scope.librarySort === 2) {
            queryParams.limit = 10;
        }

        Restangular.one('library/list').get(queryParams).then(function(response) {
            $scope.books = response.books;
            console.log("Fetched books:", $scope.books);
        }, function(response) {
            console.error("Error fetching books:", response);
        });
    };

    $scope.updateSortCriterion = function(criterion) {
        $scope.librarySort = criterion;
        $scope.fetchBooks(); // Refetch the books based on the new sorting criterion
    };

    // Initialize filters (this could be dynamic based on another API or hardcoded values)
    $scope.availableAuthors = ['Author 1', 'Author 2']; // Example, replace with dynamic values if needed
    $scope.availableGenres = ['Action', 'Thriller', 'Comedy', 'Horror']; // Example, replace with dynamic values if needed
    $scope.availableRatings = ['1','2','3','4','5','6', '7', '8', '9'];

    $scope.viewBookDetails = function(bookId) {
        $state.go('libraryBookDetail', { bookId: bookId }); // Navigate using bookId
    };

    // Call fetchBooks on controller initialization
    $scope.fetchBooks();
}]);
