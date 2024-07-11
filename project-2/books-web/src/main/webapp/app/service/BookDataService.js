App.factory('BookDataService', function() {
    var bookData = {};

    return {
        setBookData: function(data) {
            console.log("Received some data from library");
            bookData = data;
        },
        getBookData: function() {
            console.log("Someone retrieving some data from library");
            return bookData;
        }
    };
});