select m.year as Year, avg(r.rating) as AverageRating, count(*) as RecordNum
from (select distinct movieid, year from movie) as m, (select movieid, rating from rating) as r
where m.movieid = r.movieid group by m.Year;

select m.Genres as Genre, avg(r.rating) as AverageRating, count(*) as RecordNum
from (select movieid, genres from movie) as m, (select movieid, rating from rating) as r
where m.movieid = r.movieid group by m.Genres;
