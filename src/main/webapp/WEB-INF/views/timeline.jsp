<!DOCTYPE HTML>
<html>
<head>
<meta charset=utf-8>
<title>HasLearnt.it</title>

<script src="js/modernizr-1.7.min.js"></script>

<link rel="stylesheet" type="text/css" href="css/reset.css" media="screen" title="html5doctor.com Reset Stylesheet" />
<link rel="stylesheet" type="text/css" href="css/css3.css" media="screen" />
<link rel="stylesheet" type="text/css" href="css/general.css" media="screen" />
<link rel="stylesheet" type="text/css" href="css/grid.css" media="screen" />
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/ui-lightness/jquery-ui.css" type="text/css" media="all" />

<link rel="stylesheet" type="text/css" href="css/haslearntit.css" media="screen" />

<script src="https://www.google.com/jsapi?key=ABQIAAAAUxUCpv_-wchVMBPB7LVQnRToRG0_EwnD46K32g5KL4I8_QPFRhSN0FGRemLxhNwTuWDoZMMht4aUrA"
    type="text/javascript"></script>

<script type="text/javascript">
	google.load("jquery", "1.7.1");
	google.load("jqueryui", "1.8.16");

	function OnLoad() {
		$(document).ready(function() {
			$("#datepicker").datepicker({
				   onSelect: function(dateText, inst) {
					   $('#dropdown-choose-date').text(dateText);
					   hideDiv($(this));
				   }
				});

			$("div.clickable").click(function() {
				window.location = $(this).attr("url");
			});

			$(".clickable-text").click(function(e) {
				var name = '#' + $(this).attr("dropdown-name");
				if ($(name).is(":visible")) {
					hideDiv($(name));
				} else {
					var position = $(this).offset();
					$(name).offset({
						top : position.top + 30,
						left : position.left
					});
					$(name).show();
				}
			});

			$(".dropdown-selection").click(function(e) {
				var selection = $(this).text();
				var dropdown = $(this).parent().parent().parent();
				$('#dropdown-' + dropdown.attr("id")).text(selection);
				hideDiv(dropdown);
			});
			
			$(".comment-textfield").submit(function() {			
				var date = new Date();
				$(this).parent().parent().prepend(
						'<div class="entry-photo"><img src="http://gravatar.com/avatar/0606d1268f2a8198d7fd601c7350acb9" /></div>' +
                        '<div class="entry-text comment-container"><a class="comment-author" href="#">Paweł Lipiński</a><br /><p class="comment-text">'+  
                        $('#' + $(this).parent().parent().attr('id') + '-input').val() + 
                        '</p><span class="entry-date">Dzisiaj, ' + date.getHours() + ':' + date.getMinutes() +'</span></div><div class="entry-hr"></div>');
			});
		});
	}
	google.setOnLoadCallback(OnLoad);
	
	function hideDiv(div) {
		div.offset({
			top : 0,
			left : 0
		});
		div.hide();
	}
</script>
</head>

<body>
    <div class="row">
        <div id="main">
            <header  id="page-header" style="background: url('img/button-bg.jpg') repeat-x;">
                <div class="left">
                    <img src="img/logo.jpg" />
                </div>
                <div id="search">
                    <span class="label">Search</span>
                    <div id="searchForm">
                        <form>
                            <input type="text" />
                        </form>
                    </div>
                </div>
                <div id="profile" class="clickable" url="user.html">
                <span class="label">Profile</span>
                </div>
                <div id="logout" class="clickable" url="login-page.html">
                    <span class="label">Logout</span>
                </div>
            </header>

            <div class="clear"></div>

            <section id="top">
                <div class="left" id="photo">
                    <img src="http://gravatar.com/avatar/0606d1268f2a8198d7fd601c7350acb9" />
                </div>
                <div class="left left-margin">
                    <h1 style="font-size: 26pt; margin-bottom: 10px; margin-top: 10px;">
                        Hello <span class="emphasized" style="font-size: 24pt">Pawel Lipinski</span>!
                    </h1>
                    <h2 style="font-size: 18pt;">What have you learned since your last visit?</h2>
                </div>
                <div class="right" id="main-stats">
                    <div class="left" style="width: 161px; height: 100%; display: table;">
                        <p class="center-horizontal center-vertical" style="font-size: 13pt;">
                            You've been learning:<br />22h, 17min
                        </p>
                    </div>
                    <div style="float: left; width: 1px; background-color: #000; height: 100%; display: block;"></div>
                    <div class="left" style="width: 138px; height: 100%; display: table;">
                        <p class="center-horizontal center-vertical" style="font-size: 18pt;">
                            You have:<br /> <span class="emphasized">512 points</span>
                        </p>
                    </div>
                </div>
            </section>

            <div class="clear"></div>

            <section id="content">
                <div id="entry-form">
                    <form>
                    <div
                        style="background-color: #eaeaea; display: table; position: relative; top: 10px; left: 40px; width: 600px; height: 40px; font-size: 16pt; margin-bottom: 10px;">
                        <div style="display: table-cell; vertical-align: middle;">
                            <span class="left-margin clickable-text" id="dropdown-choose-date" dropdown-name="datepicker"> TODAY </span> <span
                                class="left-margin clickable-text" id="dropdown-choose-type" dropdown-name="choose-type"> I've been
                                learning </span> <span class="left-margin"> <input type="text" placeholder="Your new skill..."
                                style="height: 22px; width: 200px;" />
                            </span>
                        </div>
                    </div>
                    <div
                        style="background-color: #eaeaea; display: table; position: relative; top: 10px; left: 40px; width: 600px; height: 40px; font-size: 16pt;">
                        <div style="display: table-cell; vertical-align: middle;">
                            <span class="left-margin">It was</span><span class="left-margin clickable-text" id="dropdown-choose-level"
                                dropdown-name="choose-level">easy</span> <span class="left-margin">and it took me</span><span
                                class="left-margin"><input type="number" placeholder="..." style="height: 22px; width: 40px;" /></span> <span
                                class="left-margin clickable-text" id="dropdown-choose-time" dropdown-name="choose-time">minutes</span> <span
                                class="right" style="margin-right: 20px;"><input type="submit" id="submit-entry" value="ENTER" /></span>
                        </div>
                    </div>
                    </form>
                </div>
                <div id="wall" style="display: table; width: 100%">
                    <div class="entry-box">
                        <div class="entry-photo">
                            <img src="http://gravatar.com/avatar/b36fee01ac1166d8dd46f3c2e7649be6" />
                        </div>
                        <div style="display: table; float: left;">
                            <div class="entry-text">
                                <a href="#">Krzysztof Jelski</a> has been learning <span class="emphasized">HTML &amp; CSS</span> today.<br />
                                It was easy and it took him 3 hours (of total 18 hours)<br /> He earned 15 points!
                            </div>
                            <div class="comment-box">
                                <a href="#" style="color: #aaa;" onclick="$('#comment-1').show();">Leave a comment</a>
                            </div>
                            <div id="comment-1" class="comment-box comments" style="display: table; display: none;">
                                <div class="comment-input">
                                    <form class="comment-textfield"><input type="text" id="comment-1-input" placeholder="Leave a comment..." style="width: 300px; height: 25px;"/></form>
                                </div>
                            </div>
                        </div>
                        <div class="entry-date">Wczoraj o 19:52</div>
                    </div>
                    <div class="entry-box">
                        <div class="entry-photo">
                            <img
                                src="http://getfile0.posterous.com/getfile/files.posterous.com/temp-2011-11-28/pzdCuyqueJleCnqxvqIvexsIAFhkqzkpbitCmGnDdAtBFDcuGmpadftfaDlr/15.jpg.thumb100.jpg?content_part=pid___0" />
                        </div>
                        <div style="display: table; float: left;">
                            <div class="entry-text">
                                <a href="#">Piotr Przybylak</a> has been learning <span class="emphasized">Kamasutra</span> today.<br /> It
                                was hard and it took him 23 hours (of total 152738392 hours)<br /> He earned 69 points!
                            </div>
                            <div class="comment-box comments">
                                <div class="entry-photo">
                                    <img src="http://gravatar.com/avatar/0606d1268f2a8198d7fd601c7350acb9" />
                                </div>
                                <div class="entry-text comment-container">
                                    <a class="comment-author" href="#">Paweł Lipiński</a><br />
                                    <p class="comment-text">"So let's do it like they do it on Discovery channel"</p>
                                    <span class="entry-date">Dzisiaj, 7:15</span>
                                </div>
                                <div class="entry-hr"></div>
                                <div class="entry-photo">
                                    <img src="http://gravatar.com/avatar/49f4023a979fe8e47c7b639697f86f00" />
                                </div>
                                <div class="entry-text comment-container">
                                    <a class="comment-author" href="#">Jakub Nabrdalik</a><br />
                                    <p class="comment-text">Daj lepiej linka do tej strony z Viagrą...</p>
                                    <span class="entry-date">Dzisiaj, 23:15</span>
                                </div>
                                <div class="entry-hr"></div>
                                <div class="comment-input">
                                    <input type="text" placeholder="Leave a comment..." />
                                </div>
                            </div>
                        </div>
                        <div class="entry-date">Wczoraj o 19:52</div>
                    </div>
                    <div class="clear"></div>
                    <div id="footer">HasLearnt.it &copy; 2011</div>
                    <div class="clear"></div>
                </div>
            </section>
        </div>

        <section id="left-sidebar" class="sidebar rounded">
            <section>
                <p class="title">You're following:</p>
                <table>
                    <tr>
                        <td class="img"><img src="http://gravatar.com/avatar/b36fee01ac1166d8dd46f3c2e7649be6" /></td>
                        <td class="name"><a href="#">Krzysiek Jelski</a> <br />(1200)</td>
                    </tr>
                    <tr>
                        <td class="img"><img src="http://gravatar.com/avatar/49f4023a979fe8e47c7b639697f86f00" /></td>
                        <td class="name"><a href="#">Jakub Nabrdalik</a><br />(1023)</td>
                    </tr>
                    <tr>
                        <td class="img"><img src="http://gravatar.com/avatar/69eec1703cac03acdd3f80637c6a2b7e" /></td>
                        <td class="name"><a href="#">Tomasz Bartczak</a><br />(918)</td>
                    </tr>
                    <tr>
                        <td class="img"><img src="http://gravatar.com/avatar/49f4023a979fe8e47c7b639697f86f00" /></td>
                        <td class="name"><a href="#">Jakub Nabrdalik</a><br />(1023)</td>
                    </tr>
                    <tr>
                        <td class="img"><img src="http://gravatar.com/avatar/69eec1703cac03acdd3f80637c6a2b7e" /></td>
                        <td class="name"><a href="#">Tomasz Bartczak</a><br />(918)</td>
                    </tr>
                </table>
                <p class="center-horizontal">
                    <a href="#">See more...</a>
                </p>
            </section>
            <hr />
            <section>
                <p class="title">Your followers:</p>
                <table>
                    <tr>
                        <td class="img"><img src="http://gravatar.com/avatar/b36fee01ac1166d8dd46f3c2e7649be6" /></td>
                        <td class="name"><a href="#">Krzysiek Jelski</a> <br />(1200)</td>
                    </tr>
                    <tr>
                        <td class="img"><img src="http://gravatar.com/avatar/49f4023a979fe8e47c7b639697f86f00" /></td>
                        <td class="name"><a href="#">Jakub Nabrdalik</a><br />(1023)</td>
                    </tr>
                    <tr>
                        <td class="img"><img src="http://gravatar.com/avatar/69eec1703cac03acdd3f80637c6a2b7e" /></td>
                        <td class="name"><a href="#">Tomasz Bartczak</a><br />(918)</td>
                    </tr>
                    <tr>
                        <td class="img"><img src="http://gravatar.com/avatar/49f4023a979fe8e47c7b639697f86f00" /></td>
                        <td class="name"><a href="#">Jakub Nabrdalik</a><br />(1023)</td>
                    </tr>
                    <tr>
                        <td class="img"><img src="http://gravatar.com/avatar/69eec1703cac03acdd3f80637c6a2b7e" /></td>
                        <td class="name"><a href="#">Tomasz Bartczak</a><br />(918)</td>
                    </tr>
                </table>
                <p class="center-horizontal">
                    <a href="#">See more...</a>
                </p>
            </section>
            <hr />
            <section>
                <p class="title">Invite friends:</p>
                <form id="inviteForm">
                    <input type="email"><br />
                    <p style="font-size: 10pt; text-align: center;">Type your friend's email</p>
                    <p style="font-size: 14pt; text-align: center; margin-bottom: 20px;">
                        <input type="submit" value="INVITE" />
                    </p>
                </form>
            </section>
        </section>
        <section id="right-sidebar" class="sidebar rounded">
            <section>
                <p class="title">Trends:</p>
                <ol>
                    <li><a href="#">JAVA cośtam</a><br />128 users</li>
                    <li><a href="#">MySQL cośtam</a><br />110 users</li>
                    <li><a href="#">HTML5 &amp; CSS3</a><br />98 users</li>
                    <li><a href="#">MySQL cośtam</a><br />110 users</li>
                    <li><a href="#">HTML5 &amp; CSS3</a><br />98 users</li>
                </ol>
            </section>
            <hr />
            <section>
                <p class="title">Top 5 users:</p>
                <table>
                    <tr>
                        <td class="img"><img src="http://gravatar.com/avatar/b36fee01ac1166d8dd46f3c2e7649be6" /></td>
                        <td class="name"><a href="#">Krzysiek Jelski</a> <br />(1200)</td>
                    </tr>
                    <tr>
                        <td class="img"><img src="http://gravatar.com/avatar/49f4023a979fe8e47c7b639697f86f00" /></td>
                        <td class="name"><a href="#">Jakub Nabrdalik</a><br />(1023)</td>
                    </tr>
                    <tr>
                        <td class="img"><img src="http://gravatar.com/avatar/69eec1703cac03acdd3f80637c6a2b7e" /></td>
                        <td class="name"><a href="#">Tomasz Bartczak</a><br />(918)</td>
                    </tr>
                    <tr>
                        <td class="img"><img src="http://gravatar.com/avatar/49f4023a979fe8e47c7b639697f86f00" /></td>
                        <td class="name"><a href="#">Jakub Nabrdalik</a><br />(1023)</td>
                    </tr>
                    <tr>
                        <td class="img"><img src="http://gravatar.com/avatar/69eec1703cac03acdd3f80637c6a2b7e" /></td>
                        <td class="name"><a href="#">Tomasz Bartczak</a><br />(918)</td>
                    </tr>
                </table>
            </section>
            <hr />
            <section>
                <p class="title">Suggestions:</p>
                <ol>
                    <li><a href="#">JAVA cośtam</a><br />128 users</li>
                    <li><a href="#">MySQL cośtam</a><br />110 users</li>
                    <li><a href="#">HTML5 &amp; CSS3</a><br />98 users</li>
                </ol>
            </section>
        </section>
    </div>
    <div id="datepicker" class="dropdown"></div>    
    <div class="dropdown" style="width: 190px; height: 55px;" id="choose-type">
        <ul style="list-style: none; line-height: 150%;">
            <li><a href="#" class="dropdown-selection">I've been learning</a></li>
            <li><a href="#" class="dropdown-selection">I've learnt</a></li>
        </ul>
    </div>
    <div class="dropdown" style="width: 120px; height: 80px;" id="choose-level">
        <ul style="list-style: none; line-height: 150%;">
            <li><a href="#" class="dropdown-selection">easy</a></li>
            <li><a href="#" class="dropdown-selection">medium</a></li>
            <li><a href="#" class="dropdown-selection">hard</a></li>
        </ul>
    </div>
    <div class="dropdown" style="width: 120px; height: 80px;" id="choose-time">
        <ul style="list-style: none; line-height: 150%;">
            <li><a href="#" class="dropdown-selection">minutes</a></li>
            <li><a href="#" class="dropdown-selection">hours</a></li>
            <li><a href="#" class="dropdown-selection">days</a></li>
        </ul>
    </div>
</body>
</html>
