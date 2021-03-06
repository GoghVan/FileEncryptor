package com.david.model.PICModel;
/*
图像置乱与解密算法 
 */
import java.util.*;
public class MyAlgorithms {
	/**
	 * 加解密时使用，选择法排序，同时生成地址映射表
	 */

	public static void SelectSort(double arr[], int length, HashMap hashMap, int address_arr[])
	{
		/*
			算法解释：
			i,是当前位置的下标，arr[i]是当前位置的元素，
			由于选择法每一趟排序过后，当前位置一定是之后范围内最小元素。
			也就是说，它现在的位置就是整个排序完成后的位置。

			所以，可以在一轮排序之后，直接对当前元素生成它的地址映射。
			m<double,int>是对原混沌序列的值和下标的映射，

			即map[x]的意义为x在原序列中的下标值。

			而address_arr是地址映射表，它的下标意为原序列的那一个元素，

			它的值意为，那个元素在经过排序的序列中的下标。
		*/
		if (arr == null || length <= 0) return;
		int index = 0;
		for (int i = 0; i < length; ++i)
		{
			index = i;
			for (int j = i; j < length; ++j)
			{
				if (arr[j] < arr[index]) index = j;
			}
			if (index != i)
			{
				//swap(arr[i], arr[index]);
				double temp;
				temp = arr[i];
				arr[i] = arr[index];
				arr[index] = temp;
			}
			int address = Integer.parseInt(String.valueOf(hashMap.get(arr[i])));
			//int address=(int)m.get(arr[i]);
			address_arr[address] = i;
		}

//		System.out.println("这里是SelectSort模块：");
//		System.out.print("这里是SelectSort模块，其中arr[]为：");
//		for (int i = 0; i < arr.length; i++) System.out.print("(" + i + " ," + arr[i] + "),");
//		System.out.println();
//		System.out.print("这里是SelectSort模块，其中address_arr[]为：");
//		for (int i = 0; i < address_arr.length; i++) System.out.print("(" + i + " ," + address_arr[i] + "),");
//		System.out.println();
	}

	//生成一个混沌序列，以x为初值
	public static void produce_logisticArray(double x, double arr[], int N)
	{
//		System.out.println("这里是produce_logisticArray模块：");
		double u = 3.9999999;
		arr[0] = x;
		for (int i = 1; i < N; ++i)
		{
			//arr[i] = 1 - 2*(arr[i - 1] * arr[i - 1]);
			arr[i] = u * arr[i - 1] * (1 - arr[i - 1]);
		}
//		System.out.print("这里是produce_logisticArray模块，其中arr[]值为：");
//		for (int i = 0; i < arr.length; i ++) System.out.print("(" + i + " ," + arr[i] + "),");
//		System.out.println();
	}

	//通过混沌序列，生成 值-下标 的反向映射
	public static void produce_map(HashMap hashMap, double logistic_array[], int N)
	{
		for (int i = 0; i < N; ++i)
		{
//			pair<double, int >p = make_pair(logistic_array[i], i);
//			m.insert(p);
			hashMap.put(logistic_array[i], i);
		}
//		System.out.println();
	}
	//行/列 置乱算法
	public double rowColumnEncrypt(int pixel[][], double x1, int i,int M, int N)
	{
		ArrayFunctions arrayFunctions = new ArrayFunctions();
		//生成混沌序列
		double logistic_array[] = new double[N] ;
		produce_logisticArray(x1, logistic_array, N);
		//建立值与下标映射的Map
		HashMap hashMap = new HashMap<String , Double>();
		produce_map(hashMap, logistic_array, N);
		//拷贝混沌序列
		double temp_logArr[] = new double[N];
		arrayFunctions.arr_copy(logistic_array, temp_logArr, N);
		int address_array[] = new int[N];
		//对混沌序列进行排序，采用选择，并且同时生产地址映射表
		SelectSort(temp_logArr, N, hashMap, address_array);
		//用一个暂存数组保存被置乱后的像素数组
		int temp[] = new int[N];
		for (int j = 0; j < N; ++j)
		{
			temp[address_array[j]] = pixel[i][j];
		}

//		System.out.print("这里是rowColumnEncrypt模块，其中temp[]值为：");
//		for (int r = 0; r < temp.length; r ++) System.out.print("(" + r + " ," + temp[r] + "),");
//		System.out.println();
//		System.out.print("这里是rowColumnEncrypt模块，其中address_array[]值为：");
//		for (int r = 0; r < address_array.length; r ++) System.out.print("(" + r + " ," + address_array[r] + "),");
//		System.out.println();

		//正式置乱原图
		for (int j = 0; j < N; ++j)
		{
			pixel[i][j] = temp[j];
		}
//		System.out.println("置乱后，pixel[][]值为：");
//		for (int v = 0; v < M; v++){
//			for (int j = 0; j < N; j++) System.out.print(pixel[v][j] + " ");
//			System.out.println();
//		}
		return logistic_array[N - 1];
	}

	//行置乱接口
	public void rowEncrypt_interface(int pixel[][], double x1, int M, int N)
	{
//		System.out.println("这里是rowEncrypt_interface模块：");
		double x = x1;
		//对每一行都进行置乱
		for (int i = 0; i < M; ++i)
		{
			x = rowColumnEncrypt(pixel, x, i, M, N);
		}
	}
	//列置乱接口
	public void columnEncrypt_interface(int pixel[][], double x1, int M, int N)
	{
//		System.out.println("这里是columnEncrypt_interface模块：");
		/*
		由于置乱的算法是每行置乱，所以不能直接进行列置乱
		要把当前二维数组转的行列转换
		即：原本的pixel[M][N] 转换成 pixel[N][M]
		然后用同样的算法进行置乱
		之后再进行一次行列转换即可
		*/
		ArrayFunctions arrayFunctions = new ArrayFunctions();
		int temp[][] = new int[N][M];
		//行列互换
		arrayFunctions.arr_change(pixel,temp, M, N);

//		System.out.println("这里是columnEncrypt_interface模块，其中pixel[][]值为：");
//		for (int i = 0; i < M; i++){
//			for (int j = 0; j < N; j++) System.out.print(pixel[i][j] + " ");
//			System.out.println();
//		}
//		System.out.print("这里是columnEncrypt_interface模块，其中temp[][]值为：");
//		for (int i = 0; i < N; i++){
//			for (int j = 0; j < M; j++) System.out.print(temp[i][j] + " ");
//			System.out.println();
//		}

		double x = x1;
		for (int i = 0; i < N; ++i)
		{
			x = rowColumnEncrypt(temp, x, i,N ,M);
		}
		int temp2[][]=new int[M][N];
		//再次行列互换
		arrayFunctions.arr_change(temp,temp2, N, M);

//		System.out.println("这里是columnEncrypt_interface模块，其中temp2[][]值为：");
//		for (int i = 0; i < M; i++){
//			for (int j = 0; j < N; j++) System.out.print(temp2[i][j] + " ");
//			System.out.println();
//		}

		//正式将原数组置乱
		for (int i = 0; i < M; ++i)
		{
			for (int j = 0; j < N; ++j)
			{
				pixel[i][j] = temp2[i][j];
			}
		}

	}

	//行/列 解密算法
	public double rowColumnDecrypt(int pixel[][], double x1, int i, int M, int N)
	{
		ArrayFunctions arrayFunctions = new ArrayFunctions();
		//生成混沌序列
		double logistic_array[] = new double[N];
		produce_logisticArray(x1, logistic_array, N);
		//建立值与下标映射的Map
		HashMap hashMap = new HashMap<String , Double>();
		produce_map(hashMap, logistic_array, N);
		//拷贝混沌序列
		double temp_logArr[] = new double[N];
		arrayFunctions.arr_copy(logistic_array, temp_logArr, N);
		//比对排序后的序列与排序前的序列的
		int address_array[] = new int[N];
		//对混沌序列进行排序，采用选择，并且同时生产地址映射表
		SelectSort(temp_logArr, N, hashMap, address_array);
		//用一个暂存数组保存被置乱后的象素数组
		int temp[] = new int[N];
		for (int j = 0; j < N; ++j)
		{
			temp[j] = pixel[i][address_array[j]];
		}

//		System.out.print("这里是rowColumnDecrypt模块，其中temp[]值为：");
//		for (int r = 0; r < temp.length; r ++) System.out.print("(" + r + " ," + temp[r] + "),");
//		System.out.println();
//		System.out.print("这里是rowColumnDecrypt模块，其中address_array[]值为：");
//		for (int r = 0; r < address_array.length; r ++) System.out.print("(" + r + " ," + address_array[r] + "),");
//		System.out.println();

		//正式解密原图
		for (int j = 0; j < N; ++j)
		{
			pixel[i][j] = temp[j];
		}
//		System.out.println("解密后，pixel[][]值为：");
//		for (int v = 0; v < M; v++){
//			for (int j = 0; j < N; j++) System.out.print(pixel[v][j] + " ");
//			System.out.println();
//		}
		return logistic_array[N - 1];
	}


	//行解密接口
	public void rowDecrypt_interface(int pixel[][], double x1, int M, int N)
	{
//		System.out.println("这里是rowDecrypt_interface模块：");
		double x = x1;
		for (int i = 0; i < M; ++i)
		{
			x = rowColumnDecrypt(pixel, x, i, M, N);
		}
	}

	//列解密接口
	public void columnDecrypt_interface(int pixel[][], double x1, int M, int N)
	{
//		System.out.println("这里是columnDecrypt_interface模块：");
		/*
		由于置乱的算法是每行置乱，所以不能直接进行列置乱
		要把当前二维数组转的行列转换
		即：原本的pixel[M][N] 转换成 pixel[N][M]
		然后用同样的算法进行置乱
		之后再进行一次行列转换即可
		*/
		ArrayFunctions arrayFunctions = new ArrayFunctions();
		int temp[][] = new int[N][M];
		//行列互换
		arrayFunctions.arr_change(pixel, temp, M, N);

//		System.out.println("这里是columnEncrypt_interface模块，其中pixel[][]值为：");
//		for (int i = 0; i < M; i++){
//			for (int j = 0; j < N; j++) System.out.print(pixel[i][j] + " ");
//			System.out.println();
//		}
//		System.out.print("这里是columnEncrypt_interface模块，其中temp[][]值为：");
//		for (int i = 0; i < N; i++){
//			for (int j = 0; j < M; j++) System.out.print(temp[i][j] + " ");
//			System.out.println();
//		}

		double x = x1;
		for (int i = 0; i < N; ++i)
		{
			x = rowColumnDecrypt(temp, x, i, N, M);
		}

		int temp2[][] = new int[M][N];
		//再次行列互换
		arrayFunctions.arr_change(temp, temp2, N, M);

//		System.out.print("这里是columnEncrypt_interface模块，其中temp2[][]值为：");
//		for (int i = 0; i < M; i++){
//			for (int j = 0; j < N; j++) System.out.print(temp2[i][j] + " ");
//			System.out.println();
//		}

		//正式对像素数组解密
		for (int i = 0; i < M; ++i)
		{
			for (int j = 0; j < N; ++j)
			{
				pixel[i][j] = temp2[i][j];
			}
		}
	}
	//置乱
	public void encrypt(int pixel[][], double x1, int M, int N)
	{
		rowEncrypt_interface(pixel, x1, M, N);
		columnEncrypt_interface(pixel, x1, M, N);
	}
	//解密
	public void decrypt(int pixel[][], double x1, int M, int N)
	{
		columnDecrypt_interface(pixel, x1, M, N);
		rowDecrypt_interface(pixel, x1, M, N);

	}
}

