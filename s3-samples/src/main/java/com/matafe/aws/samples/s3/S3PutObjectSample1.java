package com.matafe.aws.samples.s3;

import java.io.InputStream;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class S3PutObjectSample1 {

	private static final String BUCKET_NAME = "matafe-bucket-sample";

	private static AmazonS3 s3Client;

	public static void main(String[] args) throws Exception {

		s3Client = AmazonS3ClientBuilder.standard().withCredentials(new ProfileCredentialsProvider()).build();

		// create bucket if does not exists
		if (!s3Client.doesBucketExistV2(BUCKET_NAME)) {
			s3Client.createBucket(BUCKET_NAME);
			System.out.println("Bucket created: " + BUCKET_NAME);
		}

		final String fileName = "sample1.txt";

		InputStream is = null;
		try {
			is = S3PutObjectSample1.class.getResourceAsStream("/sample1.txt");
			System.out.println(String.format("Put file: %s on %s s3 bucket", new Object[] { fileName, BUCKET_NAME }));
			ObjectMetadata md = new ObjectMetadata();
			md.setContentType("text/plain");
			s3Client.putObject(new PutObjectRequest(BUCKET_NAME, fileName, is, md));
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

}
