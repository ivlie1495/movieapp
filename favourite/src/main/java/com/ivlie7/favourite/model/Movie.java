package com.ivlie7.favourite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "Movie")
public class Movie implements Parcelable {

    public Movie() {

    }

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String title;

    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @ColumnInfo(name = "vote_average")
    private float voteAverage;

    private Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readFloat();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public static Movie fromContentValues(ContentValues values) {
        Movie movie = new Movie();
        movie.setId(values.getAsInteger("id"));
        movie.setTitle(values.getAsString("name"));
        movie.setPosterPath(values.getAsString("poster_path"));
        movie.setBackdropPath(values.getAsString("backdrop_path"));
        movie.setOverview(values.getAsString("overview"));
        movie.setReleaseDate(values.getAsString("release_date"));
        movie.setVoteAverage(values.getAsFloat("vote_average"));

        return movie;
    }

    public Movie(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.title = cursor.getString(cursor.getColumnIndex("name"));
        this.posterPath = cursor.getString(cursor.getColumnIndex("poster_path"));
        this.backdropPath = cursor.getString(cursor.getColumnIndex("backdrop_path"));
        this.overview = cursor.getString(cursor.getColumnIndex("overview"));
        this.releaseDate = cursor.getString(cursor.getColumnIndex("release_date"));
        this.voteAverage = cursor.getFloat(cursor.getColumnIndex("vote_average"));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(posterPath);
        parcel.writeString(backdropPath);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeFloat(voteAverage);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }
}
