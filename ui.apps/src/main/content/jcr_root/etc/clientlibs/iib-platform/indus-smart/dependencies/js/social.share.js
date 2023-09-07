// ----------------------------------------------------------------------------------------------
// ++ repeat below Jquery for every share with change bothe ids #socialShare and #socialGallery ++
// ----------------------------------------------------------------------------------------------

// add this rail gallery effect
$(document).on('click', '.socialShare', function() {
    var currentArticleHref = window.location.origin + $(this).parents('.card').find('.card-body a').attr('href');
    var self = $('.socialGallery');
    var element = $('.socialGallery a');
    var c = 0;

    element.each(function(index, article) {
        var $article = $(article),
            newLink = $article.attr('shareLink') + currentArticleHref;
        if($article.attr('shareLink')) {
            $article.attr("href", newLink);
        }    
    });
    if (self.hasClass('animate')) {
        return;
    }

    if (!self.hasClass('open')) {

        self.addClass('open');

        TweenMax.staggerTo(element, 0.3, {
                opacity: 1,
                visibility: 'visible'
            },
            0.075);
        TweenMax.staggerTo(element, 0.3, {
                top: -12,
                ease: Cubic.easeOut
            },
            0.075);

        TweenMax.staggerTo(element, 0.2, {
                top: 0,
                delay: 0.1,
                ease: Cubic.easeOut,
                onComplete: function() {
                    c++;
                    if (c >= element.length) {
                        self.removeClass('animate');
                    }
                }
            },
            0.075);

        self.addClass('animate');

    }

    // else {

    //     TweenMax.staggerTo(element, 0.3, {
    //             opacity: 0,
    //             onComplete: function() {
    //                 c++;
    //                 if (c >= element.length) {
    //                     self.removeClass('open animate');
    //                     element.css('visibility', 'hidden');
    //                 };
    //             }
    //         },
    //         0.075);
    // }
});

$(document).on('click', '.socialGallery .cross', function() {
    var self = $('.socialGallery');
    var element = $('.socialGallery a');
    var c = 0;

    // if (self.hasClass('animate')) {
    //     return;
    // }

    if (self.hasClass('open')) {

        TweenMax.staggerTo(element, 0.3, {
                opacity: 0,
                onComplete: function() {
                    c++;
                    if (c >= element.length) {
                        self.removeClass('open animate');
                        element.css('visibility', 'hidden');
                    };
                }
            },
            0.075);

    }


});