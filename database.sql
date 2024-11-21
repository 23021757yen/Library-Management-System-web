-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 19, 2024 lúc 12:59 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `myoop`
--

DELIMITER $$
--
-- Các hàm
--
CREATE DEFINER=`root`@`localhost` FUNCTION `generateRandomID` (`len` INT) RETURNS VARCHAR(255) CHARSET utf8mb4 COLLATE utf8mb4_general_ci DETERMINISTIC BEGIN
    DECLARE chars VARCHAR(62) DEFAULT 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    DECLARE result VARCHAR(255) DEFAULT '';
    DECLARE i INT DEFAULT 0;

    WHILE i < len DO
        SET result = CONCAT(result, SUBSTRING(chars, FLOOR(1 + RAND() * CHAR_LENGTH(chars)), 1));
        SET i = i + 1;
    END WHILE;

    RETURN result;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `admin`
--

CREATE TABLE `admin` (
  `admin_ID` varchar(10) NOT NULL DEFAULT '',
  `password` varchar(100) NOT NULL DEFAULT 'root'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `author`
--

CREATE TABLE `author` (
  `au_ID` varchar(20) DEFAULT '',
  `tacpham` text DEFAULT NULL,
  `name` varchar(255) NOT NULL DEFAULT '',
  `tieusu` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `books`
--

CREATE TABLE `books` (
  `title` varchar(255) DEFAULT NULL,
  `amount` int(11) DEFAULT 100,
  `description` text DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `yearPublic` year(4) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `kind` varchar(50) DEFAULT NULL,
  `book_ID` int(11) NOT NULL,
  `author` varchar(255) NOT NULL,
  `image` varchar(255) NOT NULL,
  `addDate` date NOT NULL DEFAULT current_timestamp(),
  `viewCount` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `books`
--

INSERT INTO `books` (`title`, `amount`, `description`, `location`, `yearPublic`, `price`, `kind`, `book_ID`, `author`, `image`, `addDate`, `viewCount`) VALUES
('The Distinction of Fiction', 10, 'Winner of the Aldo and Jeanne Scaglione Prize for Comparative Literary Studies from the Modern Language Association Winner of the Modern Language Association\'s Aldo and Jeanne Scaglione Prize for Comparative Literary Studies The border between fact and fiction has been trespassed so often it seems to be a highway. Works of history that include fictional techniques are usually held in contempt, but works of fiction that include history are among the greatest of classics. Fiction claims to be able to convey its own unique kinds of truth. But unless a reader knows in advance whether a narrative is fictional or not, judgment can be frustrated and confused. In The Distinction of Fiction, Dorrit Cohn argues that fiction does present specific clues to its fictionality, and its own justifications. Indeed, except in cases of deliberate deception, fiction achieves its purposes best by exercising generic conventions that inform the reader that it is fiction. Cohn tests her conclusions against major narrative works, including Proust\'s A la Recherche du temps perdu, Mann\'s Death in Venice, Tolstoy\'s War and Peace, and Freud\'s case studies. She contests widespread poststructuralist views that all narratives are fictional. On the contrary, she separates fiction and nonfiction as necessarily distinct, even when bound together. An expansion of Cohn\'s Christian Gauss lectures at Princeton and the product of many years of labor and thought, The Distinction of Fiction builds on narratological and phenomenological theories to show that boundaries between fiction and history can be firmly and systematically explored.', 'somewhere in library', '2015', 200, NULL, 1, 'Dorrit Cohn', 'http://books.google.com/books/content?id=5Zpq0Rmm7hQC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '2024-11-11', 37),
('John Fowles\'s Fiction and the Poetics of Postmodernism', 10, 'Salami presents, for instance, a critique of the self-conscious narrative of the diary form in The Collector, the intertextual relations of the multiplicity of voices, the problems of subjectivity, the reader\'s position, the politics of seduction, ideology, and history in The Magus and The French Lieutenant\'s Woman. The book also analyzes the ways in which Fowles uses and abuses the short-story genre, in which enigmas remain enigmatic and the author disappears to leave the characters free to construct their own texts. Salami centers, for example, on A Maggot, which embodies the postmodernist technique of dialogical narrative, the problem of narrativization of history, and the explicitly political critique of both past and present in terms of social and religious dissent. These political questions are also echoed in Fowles\'s nonfictional book The Aristos, in which he strongly rejects the totalization of narratives and the materialization of society.', 'somewhere in library', '2015', 200, 'Language Arts & Disciplines', 23, 'Mahmoud Salami', 'http://books.google.com/books/content?id=EquizXeXb3kC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '2024-11-13', 62),
('Handbook of Formative Assessment in the Disciplines', 10, 'The Handbook of Formative Assessment in the Disciplines meaningfully addresses current developments in the field, offering a unique and timely focus on domain dependency. Building from an updated definition of formative assessment, the book covers the integration of measurement principles into practice; the operationalization of formative assessment within specific domains, beyond generic strategies; evolving research directions including student involvement and self-regulation; and new approaches to the challenges of incorporating formative assessment training into pre-service and in-service educator training. As supporters of large-scale testing programs increasingly consider the potential of formative assessments to improve teaching and learning, this handbook advances the subject through novel frameworks, intersections of theory, research, and practice, and attention to discernible disciplines. Written for instructors, graduate students, researchers, and policymakers, each chapter provides expert perspectives on the procedures and evaluations that enable teachers to adapt teaching and learning in-process toward student achievement.', 'somewhere in library', '2015', 200, 'Education', 24, 'Heidi L. Andrade, Randy E. Bennett, Gregory J. Cizek', 'http://books.google.com/books/content?id=jj73DwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '2024-11-13', 8),
('Fitzgerald\'s Craft of Short Fiction', 10, 'Normal0falsefalsefalseMicrosoftInternetExplorer4 Fitzgerald\'s Craft of Short Fiction offers the first comprehensive study of the four collections of short stories that F. Scott Fitzgerald (1896-1940) prepared for publication during his lifetime: Flappers and Philosophers (1920), Tales of the Jazz Age (1922), All the Sad Young Men (1926), and Taps at Reveille (1935). These authorized collections--which include works from the entire range of Fitzgerald\'s career, from his undergraduate days at Princeton to his final contributions to Esquire magazine--provide an ideal overview of his development as a short story writer. Originally published in 1989, this volume draws upon Fitzgerald\'s copious personal correspondence, biographical studies, and all available criticism, and analyzes how Fitzgerald perceived his achievements as a writer of short fiction from both artistic and commercial standpoints. Petry pays close attention to the individual stories, exploring how Fitzgerald\'s growing technical expertise and the evolution of his themes reflect changes in his personal life.', 'somewhere in library', '2015', 200, 'Literary Criticism', 25, 'Alice Hall Petry', 'http://books.google.com/books/content?id=gSVfmIScfJIC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '2024-11-13', 12),
('New Boundaries in Political Science Fiction', 10, 'Surveying the vast expanse of politically-charged science fiction, this book posits that the defining dilemma for these tales rests in whether identity and meaning germinate from progressive linear changes or progress, or from a continuous return to primitive realities of war, death and the competition for survival.', 'somewhere in library', '2015', 200, 'Fiction', 26, 'Donald M. Hassler, Clyde Wilcox', 'http://books.google.com/books/content?id=-8iD6iuO-iAC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '2024-11-13', 22),
('The Art of Fiction', 10, NULL, 'somewhere in library', '2015', 200, 'Fiction', 27, 'Walter Besant', 'http://books.google.com/books/content?id=QkRMAAAAMAAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '2024-11-14', 2),
('The Supernatural in Short Fiction of the Americas', 10, 'The continuing cultural encounters of the Americas, between European and indigenous cultures, and between scientific materialism and premodern supernaturalism, have originated new narrative forms. While supernatural short fiction of the Americas belongs to the broad category of the fantastic, which is generally approached synchronically, reading audiences of the past 200 years have shifted their beliefs about the supernatural several times. While nineteenth-century readers understood science as real and the supernatural as imaginary, modern audiences recognize both as inaccurate, a shift which allows authors of supernatural fiction to celebrate premodern indigenous beliefs which were once disdained by a materialist culture. This book situates supernatural short fiction of the Americas within the changing cultural and epistemological contexts of the last 200 years and explores how authors have drawn upon a wealth of indigenous traditions. The book begins with a discussion of theories of the supernatural and the fantastic. It then looks at some of the first encounters of European and Native American supernatural beliefs and points to the common elements of these early traditions. The volume next focuses on American literature of the nineteenth century, which has a complex fusion of materialist biases and metaphysical fascinations. The final portion of the book gives greater attention to Spanish-American literature and the blending of the supernatural with attitudes of nostalgia and uncertainty.', 'somewhere in library', '2015', 200, 'Literary Criticism', 28, 'Dana Del George', 'http://books.google.com/books/content?id=duPEEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '2024-11-14', 5),
('A Companion to Sensation Fiction', 10, 'This comprehensive collection offers a complete introduction to one of the most popular literary forms of the Victorian period, its key authors and works, its major themes, and its lasting legacy. Places key authors and novels in their cultural and historical context Includes studies of major topics such as race, gender, melodrama, theatre, poetry, realism in fiction, and connections to other art forms Contributions from top international scholars approach an important literary genre from a range of perspectives Offers both a pre and post-history of the genre to situate it in the larger tradition of Victorian publishing and literature Incorporates coverage of traditional research and cutting-edge contemporary scholarship', 'somewhere in library', '2015', 200, 'Literary Criticism', 29, 'Pamela K. Gilbert', 'http://books.google.com/books/content?id=1M_XknhqA-MC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '2024-11-15', 26),
('A Companion to Sensation Fiction', 10, 'This comprehensive collection offers a complete introduction to one of the most popular literary forms of the Victorian period, its key authors and works, its major themes, and its lasting legacy. Places key authors and novels in their cultural and historical context Includes studies of major topics such as race, gender, melodrama, theatre, poetry, realism in fiction, and connections to other art forms Contributions from top international scholars approach an important literary genre from a range of perspectives Offers both a pre and post-history of the genre to situate it in the larger tradition of Victorian publishing and literature Incorporates coverage of traditional research and cutting-edge contemporary scholarship', 'somewhere in library', '2015', 200, 'Literary Criticism', 30, 'Pamela K. Gilbert', 'http://books.google.com/books/content?id=1M_XknhqA-MC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '2024-11-15', 1),
('A Companion to Sensation Fiction', 10, 'This comprehensive collection offers a complete introduction to one of the most popular literary forms of the Victorian period, its key authors and works, its major themes, and its lasting legacy. Places key authors and novels in their cultural and historical context Includes studies of major topics such as race, gender, melodrama, theatre, poetry, realism in fiction, and connections to other art forms Contributions from top international scholars approach an important literary genre from a range of perspectives Offers both a pre and post-history of the genre to situate it in the larger tradition of Victorian publishing and literature Incorporates coverage of traditional research and cutting-edge contemporary scholarship', 'somewhere in library', '2015', 200, 'Literary Criticism', 31, 'Pamela K. Gilbert', 'http://books.google.com/books/content?id=1M_XknhqA-MC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '2024-11-15', 1),
('Schools of Fiction', 10, 'In Schools of Fiction, Morgan Day Frank considers a bizarre but integral feature of the modern educational experience: that teachers enthusiastically teach literary works that have terrible things to say about school. From Ishmael\'s insistence in Herman Melville\'s Moby-Dick that a whale-ship was my Yale College and my Harvard, to the unnamed narrator\'s expulsion from his southern college in Ralph Ellison\'s Invisible Man, the most frequently taught books in the English curriculum tend to be those that cast the school as a stultifying and inhumane social institution. Why have educators preferred the anti-scholasticism of the American romance tradition to the didacticism of sentimentalists? Why have they organized African American literature as a discursive category around texts that despaired of the post-Reconstruction institutional system? Why did they start teaching novels, that literary form whose very nature, in Mikhail Bakhtin\'s words, is not canonic? Reading literature in class is a paradoxical undertaking that, according to Day Frank, has proved foundational to the development of American formal education over the last two centuries, allowing the school to claim access to a social world external to itself. By drawing attention to the transformative effect literature has had on the school, Schools of Fiction challenges some of our core assumptions about the nature of cultural administration and the place of English in the curriculum. The educational system, Day Frank argues, has depended historically on the cultural objects whose existence it is ordinarily thought to govern and the academic subject it is ordinarily thought to have marginalized.', 'somewhere in library', '2015', 200, 'Education', 32, 'Morgan Day Frank', 'http://books.google.com/books/content?id=PvegEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '2024-11-15', 1),
('Genesis: History, Fiction, or Neither?', 10, 'The nature of the Genesis narrative has sparked much debate among Christians. This book introduces three predominant interpretive genres and their implications for biblical understanding. Each contributor identifies their position on the genre or genres of Genesis, chapters 1-11, addresses why their interpretation is respectful of and appropriate to the text, and contributes examples of its application to a variety of passages. The positions include: Theological History(Genesis can be taken seriously as both history and theology) – defended by James K. Hoffmeier. Proto-History (the early Genesis narratives consist of a variety of literary genres; which, nonetheless, do not obscure the book\'s theological teaching) – defended by Gordon J. Wenham. Ancient Historiography (an understanding of Genesis that seeks to reconcile the limitations of its human authors with the nature of it being the Word of God) defended by Kenton L. Sparks. General editor and Old Testament scholar Charles Halton explains the importance of genre and provides historical insight in the introduction and helpful summaries of each position in the conclusion. In the reader-friendly Counterpoints format, this book helps readers to reflect on the strengths and weaknesses of each view and draw informed conclusions in this much-debated topic.', 'somewhere in library', '2015', 200, 'Religion', 33, 'James K. Hoffmeier, Gordon John Wenham, Kenton Sparks', 'http://books.google.com/books/content?id=nfN9BAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '2024-11-15', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `borrow`
--

CREATE TABLE `borrow` (
  `book_ID` int(11) DEFAULT NULL,
  `endDate` datetime NOT NULL DEFAULT current_timestamp(),
  `dueDate` datetime NOT NULL DEFAULT current_timestamp(),
  `User_ID` varchar(20) NOT NULL,
  `status` varchar(20) DEFAULT NULL,
  `overTime` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `fine`
--

CREATE TABLE `fine` (
  `fine_ID` int(11) NOT NULL,
  `dueDate` datetime NOT NULL DEFAULT current_timestamp(),
  `endDate` datetime NOT NULL DEFAULT current_timestamp(),
  `reason` varchar(255) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'done',
  `User_ID` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `highlightbook`
--

CREATE TABLE `highlightbook` (
  `user_ID` varchar(20) NOT NULL,
  `book_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `highlightbook`
--

INSERT INTO `highlightbook` (`user_ID`, `book_ID`) VALUES
('bnzNlV6rpM', 26);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhaxb`
--

CREATE TABLE `nhaxb` (
  `name` varchar(255) NOT NULL DEFAULT '',
  `address` varchar(255) DEFAULT NULL,
  `contact` varchar(255) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `recentbooks`
--

CREATE TABLE `recentbooks` (
  `user_ID` varchar(20) NOT NULL,
  `book_ID` int(11) NOT NULL,
  `time` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `recentbooks`
--

INSERT INTO `recentbooks` (`user_ID`, `book_ID`, `time`) VALUES
('bnzNlV6rpM', 29, '2024-11-19 04:10:59'),
('bnzNlV6rpM', 26, '2024-11-18 18:25:25'),
('bnzNlV6rpM', 24, '2024-11-18 17:48:28');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `User_ID` varchar(20) NOT NULL DEFAULT '',
  `email` varchar(50) NOT NULL DEFAULT '',
  `name` varchar(100) DEFAULT NULL,
  `password` varchar(100) NOT NULL DEFAULT 'root',
  `phone` varchar(20) NOT NULL DEFAULT 'active'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`User_ID`, `email`, `name`, `password`, `phone`) VALUES
('bnzNlV6rpM', 'yen@gmail.com', 'tuyen', '12345a@A', '12345678'),
('g6FpCKvVZ9', 'tuyen@gmail.com', 'tuyen', '12345', '000000000'),
('jjIAmDa9oQ', 'tuyen@gmail.com', 'tuyen', '12345', '123456789'),
('L2ybub8j3u', 'tyen123@gmail.com', 'tuyen', '12345', '123456789'),
('lMMWPH0359', 'tuyencute@gmail.com', 'tuyen23', 'Tuyen123@', '0123456789'),
('rZ7j9POdmq', 'tyen', 'tyen123', 'ngtyen123@gmail.com', '03344556677'),
('VIlmTripsi', 'tuyen123@gmail.com', 'tuyenne', 'Tuyen@123', '0000000000'),
('vSLEwrImsp', 'tuyen@gmail.com', 'tuyen', '123456', '12345678'),
('Vvyvawn0VJ', 'tuyen@gmail.com', 'tuyen1', '12345', '123456789');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_ID`);

--
-- Chỉ mục cho bảng `author`
--
ALTER TABLE `author`
  ADD PRIMARY KEY (`name`);

--
-- Chỉ mục cho bảng `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`book_ID`);

--
-- Chỉ mục cho bảng `borrow`
--
ALTER TABLE `borrow`
  ADD KEY `User_ID` (`User_ID`),
  ADD KEY `fk_book_id` (`book_ID`);

--
-- Chỉ mục cho bảng `fine`
--
ALTER TABLE `fine`
  ADD PRIMARY KEY (`fine_ID`),
  ADD KEY `User_ID` (`User_ID`);

--
-- Chỉ mục cho bảng `highlightbook`
--
ALTER TABLE `highlightbook`
  ADD KEY `fk_user_id` (`user_ID`),
  ADD KEY `first` (`book_ID`);

--
-- Chỉ mục cho bảng `nhaxb`
--
ALTER TABLE `nhaxb`
  ADD PRIMARY KEY (`name`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`User_ID`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `books`
--
ALTER TABLE `books`
  MODIFY `book_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT cho bảng `fine`
--
ALTER TABLE `fine`
  MODIFY `fine_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `borrow`
--
ALTER TABLE `borrow`
  ADD CONSTRAINT `borrow_ibfk_1` FOREIGN KEY (`User_ID`) REFERENCES `user` (`User_ID`),
  ADD CONSTRAINT `fk_book_id` FOREIGN KEY (`book_ID`) REFERENCES `books` (`book_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `fine`
--
ALTER TABLE `fine`
  ADD CONSTRAINT `fine_ibfk_1` FOREIGN KEY (`User_ID`) REFERENCES `user` (`User_ID`);

--
-- Các ràng buộc cho bảng `highlightbook`
--
ALTER TABLE `highlightbook`
  ADD CONSTRAINT `first` FOREIGN KEY (`book_ID`) REFERENCES `books` (`book_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_user_id` FOREIGN KEY (`user_ID`) REFERENCES `user` (`User_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
use library;
select * from user;
select * from books;
select * from borrow;
select * from recentbooks;
