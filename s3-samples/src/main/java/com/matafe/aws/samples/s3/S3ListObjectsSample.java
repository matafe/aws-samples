package com.matafe.aws.samples.s3;

import java.util.List;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3ListObjectsSample {

	private static final String BUCKET_NAME = "matafe-bucket-sample";

	private static AmazonS3 s3Client;

	public static void main(String[] args) throws Exception {

		s3Client = AmazonS3ClientBuilder.standard().withCredentials(new ProfileCredentialsProvider()).build();

		// create bucket if does not exists
		if (!s3Client.doesBucketExistV2(BUCKET_NAME)) {
			s3Client.createBucket(BUCKET_NAME);
			System.out.println("Bucket created: " + BUCKET_NAME);
		}

		System.out.println("All buckets from my account:");
		List<Bucket> buckets = s3Client.listBuckets();
		for (Bucket bucket : buckets) {
			System.out.println(bucket.getName());
		}

		System.out.println();
		System.out.println("Listing content from bucket: " + BUCKET_NAME);
		ObjectListing objectListing = s3Client.listObjects(BUCKET_NAME);
		for (S3ObjectSummary s3ObjectSummary : objectListing.getObjectSummaries()) {
			System.out.println(s3ObjectSummary.getKey() + ", size:" + s3ObjectSummary.getSize());
		}

	}

}
