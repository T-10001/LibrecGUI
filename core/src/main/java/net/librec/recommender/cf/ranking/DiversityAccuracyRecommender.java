/**
 * 
 */
package net.librec.recommender.cf.ranking;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

import net.librec.common.LibrecException;
import net.librec.math.algorithm.Randoms;
import net.librec.math.structure.DenseMatrix;
import net.librec.math.structure.DenseVector;
import net.librec.math.structure.SparseMatrix;
import net.librec.math.structure.SparseVector;
import net.librec.math.structure.VectorEntry;
import net.librec.recommender.ProbabilisticGraphicalRecommender;

/**
 * @author PinkySwear
 *
 */
public class DiversityAccuracyRecommender extends ProbabilisticGraphicalRecommender {
	
	private DenseMatrix itemsDeg;
	private DenseMatrix usersDeg;
	
	private int userCount = 0;
	private double userMean;
	private double alpha;
	HashMap<Integer, DenseMatrix> userDatasMap = new HashMap<>();
	
	DenseMatrix targetGraphPP;// = new DenseMatrix(numItems, numItems);
	DenseMatrix targetGraphNP;// = new DenseMatrix(numItems, numItems);
	DenseMatrix targetGraphPN;// = new DenseMatrix(numItems, numItems);
	DenseMatrix targetGraphNN;// = new DenseMatrix(numItems, numItems);
	DenseMatrix tableMatrix;
	DenseMatrix itemRecomDeg;
	
	HashMap<Integer, DenseMatrix> itemProbsMap = new HashMap<>();
	Table<Integer, Integer, Double> dataTable;
	

	@Override
	protected void setup() throws LibrecException
	{
		super.setup();
		isRanking = true;
		
	}

	@Override
	protected void eStep()
	{
		
	}

	@Override
	protected void mStep()
	{
		
	}

	@Override
	protected void trainModel() throws LibrecException
	{
		super.trainModel();
		userMean = (maxRate + minRate) / 2.0;
//		userMean = trainMatrix.mean();
		// userID, itemId, rating
		dataTable = trainMatrix.getDataTable();
//		trainMatrix.
//		for (Cell<Integer, Integer, Double> cell: trainMatrix.getDataTable().cellSet()){
//			if(cell.getColumnKey() == 1070)
//		    System.out.println(cell.getRowKey()+" "+cell.getColumnKey()+" "+cell.getValue());
//		}
//		System.out.println("value=" + dataTable.get(0, 1));
//		System.out.println("numRows = " + trainMatrix.numRows);
//		System.out.println("numUsers " + numUsers);
		// numUsers is actually number of rows  are more than
		int row = 0;
		int tableMatrixRow = 0;
		HashMap<Integer, List<DenseVector>> map = new HashMap<>();
		tableMatrix = new DenseMatrix(numRates, 3);
//		for(int userID : dataTable.rowKeySet())
//		System.out.println("cell set size = " + dataTable.cellSet().size());
//		for(int rowKey : dataTable.rowKeySet())
//		{
//			System.out.println(rowKey);
//		}
		
//		System.out.println("Columns = "  + dataTable.columnKeySet().size());
//		System.out.println("cell"dataTable.cellSet().size());
		Set<Cell<Integer, Integer, Double>> cellSet = dataTable.cellSet();
//		for(int rowNum = 0; rowNum < cellSet.size(); rowNum++)
		for(Cell<Integer, Integer, Double> cell : dataTable.cellSet())
		{
			DenseVector rowVector = new DenseVector(3);
			rowVector.set(0, cell.getRowKey());
			rowVector.set(1, cell.getColumnKey());
			rowVector.set(2, cell.getValue());
			tableMatrix.set(tableMatrixRow, 0, cell.getRowKey());
			tableMatrix.set(tableMatrixRow, 1, cell.getColumnKey());
			tableMatrix.set(tableMatrixRow, 2, cell.getValue());
			
			if(!map.containsKey(cell.getRowKey()))
			{
				List<DenseVector> rowVectorList = new ArrayList<>();
				rowVectorList.add(rowVector);
				map.put(cell.getRowKey(), rowVectorList);
			}
			else
			{
				map.get(cell.getRowKey()).add(rowVector);
			}
			
		}
		
		
		for(Map.Entry<Integer, List<DenseVector>> entry : map.entrySet())
		{
			int userID = entry.getKey();
			List<DenseVector> userRows = entry.getValue();
			// matrix for the list of rows
			DenseMatrix matrix = new DenseMatrix(userRows.size(), 3);
			int rowNum = 0;
			for(DenseVector v : userRows)
			{
				
				for(int colNum = 0; colNum < 3; colNum++)
				{
					matrix.set(rowNum, colNum, v.get(colNum));
//					System.out.print(v.get(colNum) + "    ");
				}
				row++;
//				System.out.println("");
			}
			userDatasMap.put(userID, matrix);
//			System.out.println(matrix.toString());
		}
//		System.out.println("highest userID = " + Collections.max(userDatasMap.keySet(), null));
//		System.out.println("Got here7");
//		userDatasMap.put(userID, userDenseMatrix);
//		System.out.println("Got here1");
		extractDegrees();
//		generateGOne();
		generateA2AG(true);
		makeItemProbs();
		System.out.println("Got here");
//		System.out.println(itemProbsMap.toString());
//		DenseVector v = itemsDeg.column(0);
//		double currentMax = v.getData()[0];
//		for(double d : v.getData())
//		{
//			currentMax = Math.max(currentMax, d);
//		}
//		itemRecomDeg = new DenseMatrix(currentMax, 1, )
		
////		makeVectorForEachUser();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.librec.recommender.AbstractRecommender#predict(int, int)
	 */
	@Override
	protected double predict(int userIdx, int itemIdx) throws LibrecException
	{
		if(itemProbsMap.containsKey(userIdx))
		{
//			if(itemProbsMap.get(userIdx).)
//			System.out.println(itemProbsMap.get(userIdx).toString());
			DenseMatrix mat = itemProbsMap.get(userIdx);
//			for(DenseMatrix mat : itemProbsMap.get(userIdx).)
//			{
//				
//			}
			for(int i = 0; i < mat.numRows; i++)
			{
				if(itemIdx == (int) mat.get(i, 0))
					return mat.get(i, 1);
			}
		}
//	        
//	        usersRecoms(userCount,:) = systemRecoms(1:recommNum,1);
//	        %%%%%%%%%%%%%%%%%%Calc Performance
//	        futureVotes = testData( testData(:,1)==i &testData(:,3)>userMean, :);
//	        pastVotesSize = size(trainData(trainData(:,1)==i,1),1);
//	        totalFutureVotes = size(futureVotes,1) + totalFutureVotes;
//	        PatN(userCount,1) = calcPatN(systemRecoms, futureVotes, recommNum, userMean);
//	        Nov(userCount,1) = calcSIBNovelty(systemRecoms, trainItemDeg, usersNum, recommNum);
//	        
//	        recoveryRate = calcRecovery( systemRecoms, futureVotes, recoveryRate, (itemsNum-pastVotesSize), userMean);
//	        userCount = userCount + 1;
//	        userCount
		return 0;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.librec.recommender.AbstractRecommender#predict(int, int)
	 */
	
	protected double predictDegree(int userIdx, int itemIdx) throws LibrecException
	{
		// TODO Auto-generated method stb
		
//		 userState = (trainData(trainData(:,1)==i,:)); 
		 DenseMatrix userState = userDatasMap.get(userIdx);
		 
//	        %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
//	        userState = userState(:,:);
//	        [systemRecoms1] = recommend(targetGraphPP1, targetGraphNP1, targetGraphNN1, targetGraphPN1, userState(:,:), trainItemDeg);
//		 DenseMatrix systemRecoms1;
		 //		 function [recoms] = recommend(targetGraphPP, targetGraphNP, targetGraphNN, targetGraphPN, userState, itemDeg)
//		 stateSize = size(userState,1);
		 int stateSize = userState.numRows();
//		 itemSize = size(targetGraphPP,1);
		 int itemSize = targetGraphPP.numRows();
//
//		 userMean = 2;%mean(userState(:,3));
//		 int userMean = 2;
//		 itemProb = zeros((itemSize-stateSize),2);
		 DenseMatrix itemProb = new DenseMatrix(itemSize-stateSize, 2);
//		 recomNum = 0;
		 int recomNum = 0;
//		 for i=1:itemSize
		 for(int i = 0; i < itemSize; i++)
		 {
//		     if size(find(userState(:,2)==i),1) == 0 && itemDeg(i) > -1
//			 DenseVector userItems = userState.column(1);
//			 if()
//			 {
//		         recomNum = recomNum + 1;
				 recomNum++;
//		         itemProb(recomNum,2) = 0;
				 itemProb.set(recomNum, 1, 0);
//		         itemProb(recomNum,1) = i;
				 itemProb.set(recomNum, 0, i);
//		         
//		         for j=1:stateSize
				 for(int j = 0; j < stateSize; j++)
				 {
//		             subState = userState(j,2);
					 int subState = (int) userState.get(j, 1);
//		             if itemDeg(subState,1) > 0
					 if(itemsDeg.get(subState, 1) > 0)
					 {
//		                 if userState(j,3) > userMean
						 if(userState.get(j, 2) > userMean)
						 {
//		                    itemProb(recomNum,2) = itemProb(recomNum,2) + targetGraphPP(subState, i);%*exp(-(1/50)*itemDeg(subState,1));%* exp(-(1/50)*j);%*(itemDeg(subState,1));%* exp(-(1/20)*j);
							 itemProb.set(recomNum, 1, itemProb.get(recomNum, 1) + targetGraphPP.get(subState, i));
							 //		                    itemProb(recomNum,2) = itemProb(recomNum,2) - targetGraphPN(subState, i);%*exp(-(1/50)*itemDeg(subState,1));%* exp(-(1/50)*j);%*(itemDeg(subState,1));%* exp(-(1/20)*j); 
							 itemProb.set(recomNum, 1, itemProb.get(recomNum, 1) - targetGraphPN.get(subState, i));
						 }
							 //		                 else
						 else
						 {
//		                    itemProb(recomNum,2) = itemProb(recomNum,2) + targetGraphNP(subState, i);%*exp(-(1/50)*itemDeg(subState,1));%* exp(-(1/50)*j);%*(itemDeg(subState,1));%* exp(-(1/20)*j);
		                    itemProb.set(recomNum, 1, itemProb.get(recomNum, 1) + targetGraphNP.get(subState, i));
							 //itemProb(recomNum,2) = itemProb(recomNum,2) - targetGraphNN(subState, i);%*exp(-(1/50)*itemDeg(subState,1));%* exp(-(1/50)*j);%*(itemDeg(subState,1));%* exp(-(1/20)*j); 
		                    itemProb.set(recomNum, 1, itemProb.get(recomNum, 1) - targetGraphNN.get(subState, i));
						 }
							 //		                 end
//		             end
					 }
//		         end
				 }
//		         if itemProb(recomNum,2) == 0
				 if(itemProb.get(recomNum, 1) == 0)
				 {
//		             itemProb(recomNum,2) = -1;
					 itemProb.set(recomNum, 1, -1);
				 }
//		         end
//		         itemProb(recomNum,2) = (itemProb(recomNum,2)+1)/2;%.*  (min(itemDeg(i,1)/itemMeanDeg,1)^(1/100));
				 itemProb.set(recomNum, 1, (itemProb.get(recomNum, 1)+1)/2);

//		     end
//			 }
//		 end
//		 %recoms = sortrows(itemProb,-2);
//		 recoms = itemProb(1:recomNum,:);
			 
//		 end
		 }
		 DenseMatrix recoms = itemProb.getSubMatrix(0, recomNum, 0, itemProb.numColumns);
		 //	        %%%%%%%%%%%%%%%%%%Combine Recoms
//	        tempRecom = systemRecoms1;
//		 DenseMatrix tempRecom = recoms.clone();
		 
//	        myRecoms = sortrows(tempRecom,-2); // does nothing apparently
//	        systemRecoms = myRecoms;
		 /** combRanks **/
		 int recomSize = recoms.size();
		 DenseMatrix targetRecom = recoms;
//		 recoms.
//		 DenseVector usersRecoms = tempRecom.column(0);
		 for(int row = 0; row < recoms.numRows; row++)
		 {
			 DenseVector data = recoms.row(row);
			 if(data.get(1) == itemIdx && data.get(0) == userIdx)
			 {
				 System.out.println("Rating for (user, item)=" + "(" + userIdx + "," + itemIdx + ")=" + data.get(2));
				 return data.get(2);
			 }
		 }
//	        
//	        usersRecoms(userCount,:) = systemRecoms(1:recommNum,1);
//	        %%%%%%%%%%%%%%%%%%Calc Performance
//	        futureVotes = testData( testData(:,1)==i &testData(:,3)>userMean, :);
//	        pastVotesSize = size(trainData(trainData(:,1)==i,1),1);
//	        totalFutureVotes = size(futureVotes,1) + totalFutureVotes;
//	        PatN(userCount,1) = calcPatN(systemRecoms, futureVotes, recommNum, userMean);
//	        Nov(userCount,1) = calcSIBNovelty(systemRecoms, trainItemDeg, usersNum, recommNum);
//	        
//	        recoveryRate = calcRecovery( systemRecoms, futureVotes, recoveryRate, (itemsNum-pastVotesSize), userMean);
//	        userCount = userCount + 1;
//	        userCount
		return 0;
	}
	
	private void generateGOne()
	{
		// targetData = sortedData; // already sorted
		// itemNum = max(targetData(:,2)); // already have this 
		//
		// targetGraphPP = zeros(itemNum);
		// targetGraphNP = zeros(itemNum);
		// targetGraphPN = zeros(itemNum);
		// targetGraphNN = zeros(itemNum);
		targetGraphPP = new DenseMatrix(numItems, numItems);
		targetGraphNP = new DenseMatrix(numItems, numItems);
		targetGraphPN = new DenseMatrix(numItems, numItems);
		targetGraphNN = new DenseMatrix(numItems, numItems);
		
		// userNum = max(targetData(:,1)); // already have this 
		
		for(Map.Entry<Integer, DenseMatrix> entry : userDatasMap.entrySet())
		{
			DenseMatrix userData = entry.getValue();
//		    userDegree = size(userData,1);
			int userDegree = entry.getValue().numRows;
//		    userData = sortrows(userData,4);
//			userData = getSortedMatrix(userData, 3);
	//		    for j=1:(userDegree-1)
			for(int j = 0; j < userDegree - 1; j++)
			{
	//		        if userData(j+1,3) > userMean && userData(j,3) > userMean
				if(userData.get(j + 1, 2) > userMean && userData.get(j, 2) > userMean)
				{
					// targetGraphPP(userData(j,2),userData(j+1,2)) = targetGraphPP(userData(j,2),userData(j+1,2)) + 1;
					targetGraphPP.set((int)userData.get(j, 1), (int)userData.get(j+1, 1), targetGraphPP.get((int)userData.get(j, 1), (int)userData.get(j+1, 1))+1 );
				}
				else if(userData.get(j+1, 2) > userMean && userData.get(j, 2) > userMean)
	//		    elseif userData(j+1,3) < userMean && userData(j,3) > userMean
				{
	//		            targetGraphPN(userData(j,2),userData(j+1,2)) = targetGraphPN(userData(j,2),userData(j+1,2)) + 1;
					targetGraphPN.set((int)userData.get(j, 1), (int)userData.get(j+1, 1), targetGraphPN.get((int)userData.get(j, 1), (int)userData.get(j+1, 1))+1 );
				}
	//		          elseif userData(j+1,3)> userMean && userData(j,3) < userMean
				else if (userData.get(j+1,2)> userMean && userData.get(j,2) < userMean)
				{
	//		            targetGraphNP(userData(j,2),userData(j+1,2)) = targetGraphNP(userData(j,2),userData(j+1,2)) + 1;
					targetGraphNP.set((int)userData.get(j, 1), (int)userData.get(j+1, 1), targetGraphNP.get((int)userData.get(j, 1), (int)userData.get(j+1, 1))+1 );
				}
	//		        else
				else
				{
	//		            targetGraphNN(userData(j,2),userData(j+1,2)) = targetGraphNN(userData(j,2),userData(j+1,2)) + 1;
					targetGraphNN.set((int)userData.get(j, 1), (int)userData.get(j+1, 1), targetGraphNN.get((int)userData.get(j, 1), (int)userData.get(j+1, 1))+1 );
				}
	//		        end
	//		    end
				
			}
	//		end
			
		}
		
		//		outDegPP = sum(targetGraphPP')';
		DenseVector outDegPP = new DenseVector(targetGraphPP.numRows);
		outDegPP.setAll(targetGraphPP.transpose().sum());
		//		outDegPN = sum(targetGraphPN')';
		DenseVector outDegPN = new DenseVector(targetGraphPN.numRows);
		outDegPN.setAll(targetGraphPN.transpose().sum());
		//		outDegNP = sum(targetGraphNP')';
		DenseVector outDegNP = new DenseVector(targetGraphNP.numRows);
		outDegNP.setAll(targetGraphNP.transpose().sum());
		//		outDegNN = sum(targetGraphNN')';
		DenseVector outDegNN = new DenseVector(targetGraphNN.numRows);
		outDegNN.setAll(targetGraphNN.transpose().sum());
		//		outDegP = outDegPP + outDegPN;
		DenseVector outDegP = new DenseVector(outDegPP.add(outDegNP));
		//		outDegN = outDegNN + outDegNP;
		DenseVector outDegN = new DenseVector(outDegNP.add(outDegNN));
//		for i=1:itemNum		
		for(int i = 0; i < numItems; i++)
		{
			
//			if outDegP(i,1) ~= 0
			if(outDegP.get(i) != 0)
			{
				
//				targetGraphPP(i,:) = targetGraphPP(i,:) / outDegP(i,1);
				DenseVector rowP = targetGraphPP.row(i);
				rowP = rowP.scaleEqual(1.0/outDegP.get(i));
				targetGraphNN.setRow(i, rowP);
				
//		        targetGraphPN(i,:) = targetGraphPN(i,:) / outDegP(i,1);
				DenseVector rowN = targetGraphPN.row(i);
				rowN = rowN.scaleEqual(1.0/outDegP.get(i));
				targetGraphNN.setRow(i, rowN);
			}
//					    end
//			if outDegP(i,1) ~= 0

//			if outDegN(i,1) ~= 0			
			if(outDegN.get(i) != 0)
			{
//					        targetGraphNP(i,:) = targetGraphNP(i,:) / outDegN(i,1);
				DenseVector rowP = targetGraphNP.row(i);
				rowP = rowP.scaleEqual(1.0/outDegN.get(i));
				targetGraphNP.setRow(i, rowP);
//				targetGraphNN(i,:) = targetGraphNN(i,:) / outDegN(i,1);
				DenseVector rowN = targetGraphNN.row(i);
				rowN = rowN.scaleEqual(1.0/outDegN.get(i));
				targetGraphNN.setRow(i, rowN);
			}
//					    end
//					end
		}
	}	
	
	private void generateA2AG(boolean mode)
	{
//		sortedData = sortrows(inputData, 1);
//		targetData = sortedData;
//		itemNum = max(targetData(:,2));
//
//
//		targetGraphPP = zeros(itemNum);
//		targetGraphNP = zeros(itemNum);
//		targetGraphPN = zeros(itemNum);
//		targetGraphNN = zeros(itemNum);
		targetGraphPP = new DenseMatrix(numItems, numItems);
		targetGraphNP = new DenseMatrix(numItems, numItems);
		targetGraphPN = new DenseMatrix(numItems, numItems);
		targetGraphNN = new DenseMatrix(numItems, numItems);
//
//
//		userNum = max(targetData(:,1));
//
//		for i=1:userNum
		for(Map.Entry<Integer, DenseMatrix> entry : userDatasMap.entrySet())
		{
//		    userData = targetData(targetData(:,1)==i,:);
			DenseMatrix userData = entry.getValue();
//		    double userMean = 2;
//		    userDegree = size(userData,1);
			int userDegree = userData.numRows;
			//		    for j=1:userDegree
			for(int j = 0; j < userDegree; j++)
			{
//		        for k=1:userDegree
				for(int k = 0; k < userDegree; k++)
				{
//		            if k~=j
					if(k != j)
					{
//		                if userData(j,3) > userMean && userData(k,3) > userMean
						if(userData.get(j, 2) > userMean && userData.get(k, 2) > userMean)
						{
//		                    targetGraphPP(userData(j,2),userData(k,2)) = targetGraphPP(userData(j,2),userData(k,2)) + 1;
							targetGraphPP.set((int)userData.get(j, 1), (int)userData.get(k, 1), targetGraphPP.get((int)userData.get(j, 1), (int)userData.get(k, 1)));
						}
//		                elseif userData(j,3) < userMean && userData(k,3) > userMean
						else if (userData.get(j, 2) < userMean && userData.get(k, 2) > userMean)
						{
//		                    targetGraphPN(userData(j,2),userData(k,2)) = targetGraphPN(userData(j,2),userData(k,2)) + 1;
							targetGraphPN.set((int)userData.get(j, 1), (int)userData.get(k, 1), targetGraphPN.get((int)userData.get(j, 1), (int)userData.get(k, 1))+1 );
						}
//		                elseif userData(j,3)> userMean && userData(k,3) < userMean
						else if (userData.get(j, 2) > userMean && userData.get(k, 2) < userMean)
						{
//		                    targetGraphNP(userData(j,2),userData(k,2)) = targetGraphNP(userData(j,2),userData(k,2)) + 1;
							targetGraphNP.set((int)userData.get(j, 1), (int)userData.get(k, 1), targetGraphNP.get((int)userData.get(j, 1), (int)userData.get(k, 1))+1 );

						}
//		                else
						else
						{
//		                    targetGraphNN(userData(j,2),userData(k,2)) = targetGraphNN(userData(j,2),userData(k,2)) + 1;
							targetGraphNN.set((int)userData.get(j, 1), (int)userData.get(k, 1), targetGraphNN.get((int)userData.get(j, 1), (int)userData.get(k, 1))+1 );

//		                end
						}
//		            end
					}
//		        end
				}
//		    end
			}
//		end
		}
//
//		inDegPP = sum(targetGraphPP)';
		DenseVector inDegPP = new DenseVector(targetGraphPP.numRows);
		inDegPP.setAll(targetGraphPP.transpose().sum());
//		inDegPN = sum(targetGraphPN)';
		DenseVector inDegPN = new DenseVector(targetGraphPN.numRows);
		inDegPN.setAll(targetGraphPN.transpose().sum());
//		inDegNP = sum(targetGraphNP)';
		DenseVector inDegNP = new DenseVector(targetGraphNP.numRows);
		inDegNP.setAll(targetGraphNP.transpose().sum());
//		inDegNN = sum(targetGraphNN)';
		DenseVector inDegNN = new DenseVector(targetGraphNN.numRows);
		inDegNN.setAll(targetGraphNN.transpose().sum());
//		inDegP = inDegPP + inDegNP;
		DenseVector inDegP = new DenseVector(inDegPP.add(inDegPN));
//		inDegN = inDegNN + inDegPN;
		DenseVector inDegN = new DenseVector(inDegNN.add(inDegPN));
//		outDegPP = sum(targetGraphPP')';
		DenseVector outDegPP = new DenseVector(targetGraphPP.numRows);
		outDegPP.setAll(targetGraphPP.transpose().sum());
//		outDegPN = sum(targetGraphPN')';
		DenseVector outDegPN = new DenseVector(targetGraphPN.numRows);
		outDegPN.setAll(targetGraphPN.transpose().sum());
//		outDegNP = sum(targetGraphNP')';
		DenseVector outDegNP = new DenseVector(targetGraphNP.numRows);
		outDegNP.setAll(targetGraphNP.transpose().sum());
//		outDegNN = sum(targetGraphNN')';
		DenseVector outDegNN = new DenseVector(targetGraphNN.numRows);
		outDegNN.setAll(targetGraphNN.transpose().sum());
//		outDegP = outDegPP + outDegPN;
		DenseVector outDegP = new DenseVector(outDegPP.add(outDegPN));
//		outDegN = outDegNN + outDegNP;
		DenseVector outDegN = new DenseVector(outDegNN.add(outDegNP));
		
//		for i=1:itemNum
		for(int i = 0; i < numItems; i++)
		{
//		    if mode == 2
			if(mode)
			{
//		        if inDegP(i,1)~=0
				if(inDegP.get(i) != 0)
				{
//		            targetGraphPP(:,i) = targetGraphPP(:,i) / inDegP(i,1);
					for(int j = 0; j < targetGraphPP.numRows; j++)
					{
						targetGraphPP.set(j, i, targetGraphPP.get(j, i)/inDegP.get(i));
					}
//		        end
				}
//		        if inDegN(i,1)~=0
				if(inDegN.get(i) != 0)
				{
//		            targetGraphPN(:,i) = targetGraphPN(:,i) / inDegN(i,1);
					for(int j = 0; j < targetGraphPN.numRows; j++)
					{
						targetGraphPN.set(j, i, targetGraphPN.get(j, i)/inDegN.get(i));
					}
//		        end
				}
//		        if inDegP(i,1)~=0
				if(inDegP.get(i) != 0)
				{
//		            targetGraphNP(:,i) = targetGraphNP(:,i) / inDegP(i,1);
					for(int j = 0; j < targetGraphNP.numRows; j++)
					{
						targetGraphNP.set(j, i, targetGraphNP.get(j, i)/inDegP.get(i));
					}
//		        end
				}
//		        if inDegN(i,1)~=0
				if(inDegN.get(i) != 0)
				{
//		            targetGraphNN(:,i) = targetGraphNN(:,i) / inDegN(i,1);
					for(int j = 0; j < targetGraphNN.numRows; j++)
					{
						targetGraphNN.set(j, i, targetGraphNN.get(j, i)/inDegN.get(i));
					}
//		        end
				}
			}
//		    else
			else
			{
//		        if outDegP(i,1) ~= 0
				if(outDegP.get(i) != 0)
				{
//		            targetGraphPP(i,:) = targetGraphPP(i,:) / outDegP(i,1);
					for(int j = 0; j < targetGraphPP.numRows; j++)
					{
						targetGraphPP.set(j, i, targetGraphPP.get(j, i)/outDegP.get(i));
					}
//		        end
				}
//		        if outDegP(i,1) ~= 0
				if(outDegP.get(i) != 0)
				{
//		            targetGraphPN(i,:) = targetGraphPN(i,:) / outDegP(i,1);
					for(int j = 0; j < targetGraphPN.numRows; j++)
					{
						targetGraphPN.set(j, i, targetGraphPN.get(j, i)/outDegN.get(i));
					}
					
//		        end
				}
//		        if outDegN(i,1) ~= 0
				if(outDegN.get(i) != 0)
				{
//		            targetGraphNP(i,:) = targetGraphNP(i,:) / outDegN(i,1);
					for(int j = 0; j < targetGraphNP.numRows; j++)
					{
						targetGraphNP.set(j, i, targetGraphNP.get(j, i)/outDegN.get(i));
					}
//		        end
				}
//		        if outDegN(i,1) ~= 0
				if(outDegN.get(i) != 0)
				{
//		            targetGraphNN(i,:) = targetGraphNN(i,:) / outDegN(i,1);
					for(int j = 0; j < targetGraphNN.numRows; j++)
					{
						targetGraphNN.set(j, i, targetGraphNN.get(j, i)/outDegN.get(i));
					}
//		        end
				}
			}
//		    end
			
//		end
		}
	}
	
	// function[ usersDeg, itemsDeg ] = extractDegrees(data, userNum, itemNum)
	protected void extractDegrees()
	{
//	dataSize = size(data,1);
//		DenseMatrix datasize = new DenseMatrix(numRatingLevels, numRatingLevels)
//	usersDeg = zeros(userNum,1);
		usersDeg = new DenseMatrix(numUsers, 1);
		usersDeg.setAll(0.0);
//	itemsDeg = zeros(itemNum,1);
		itemsDeg = new DenseMatrix(numItems, 1);
		itemsDeg.setAll(0.0);
//		System.out.println(trainMatrix.size());
//		for i=1:dataSize
//		for(int i = 0; i < trainMatrix.numRows; i++)
		/***
		for(int i = 0; i < dataTable.rowKeySet().size(); i++)
		{
//	    usersDeg(data(i,1),1) = usersDeg(data(i,1),1) + 1;
			usersDeg.add(dataTable.get(i, 0).intValue(), 0, usersDeg.get(dataTable.get(i, 0).intValue(), 0)+1.0);
//	    itemsDeg(data(i,2),1) = itemsDeg(data(i,2),1) + 1;
//			System.out.println();
			System.out.println(dataTable.get(i, 0));
			System.out.println("second = " + dataTable.get(i, 1));
//			itemsDeg.set(dataTable.get(i, 1).intValue(), 0, itemsDeg.get(dataTable.get(i, 1).intValue(), 0)+1.0);
//	end
		}
//	end
 * 
 */
		DenseVector columnVector = tableMatrix.column(1);
		for(int i = 0; i < numItems; i++)
		{
			if(dataTable.get(i, 1) != null)
			{
				int itemID = (int) columnVector.get(i);
				itemsDeg.set(itemID, 0, itemsDeg.get(itemID, 0) + 1.0);
			}
		}
//		DenseMatrix tableMatrix = new DenseMatrix(numRates, 3);
//		int i = 0;
////		int j = 0;
//		for(Cell<Integer, Integer, Double> cell : dataTable.cellSet())
//		{
////			itemsDegs.set(i, 0, itemDegs(cell.getRowKey()., cell.getColumnKey()) )
////			itemsDegs.set(i, 0, cell.ge)
//			tableMatrix.set(i, 0, cell.getRowKey());
//			tableMatrix.set(i, 1, cell.getRowKey());
//			tableMatrix.set(i, 2, cell.getRowKey());
//		}
		
		
	}
	
	private void makeItemProbs()
	{
//				 userMean = 2;%mean(userState(:,3));
//				 itemProb = zeros((itemSize-stateSize),2);
		 for (Map.Entry<Integer, DenseMatrix> entry : userDatasMap.entrySet())
		{
			// TODO Auto-generated method stb
			//		 userState = (trainData(trainData(:,1)==i,:)); 
			DenseMatrix userState = entry.getValue();//userDatasMap.get(userIdx);
			//	        %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			//	        userState = userState(:,:);
			//	        [systemRecoms1] = recommend(targetGraphPP1, targetGraphNP1, targetGraphNN1, targetGraphPN1, userState(:,:), trainItemDeg);
			//		 DenseMatrix systemRecoms1;
			//		 function [recoms] = recommend(targetGraphPP, targetGraphNP, targetGraphNN, targetGraphPN, userState, itemDeg)
			//		 stateSize = size(userState,1);
			int stateSize = userState.numRows();
			int itemSize = targetGraphPP.numRows();
			//
			//				 System.out.println("stateSize = " + stateSize + ", itemSize = " + itemSize + ", itemsDeg.rows = " + itemsDeg.numRows);
			//				 System.out.println( + ", itemSize = " + itemSize);
			DenseMatrix itemProb = new DenseMatrix(itemSize - stateSize, 2);
			//				 System.out.println("itemProbRows = " + itemProb.numRows);
			//				 recomNum = 0;
			int recomNum = 0;
			//				 for i=1:itemSize
			for (int i = 0; i < itemSize; i++)
			{
				//				     if size(find(userState(:,2)==i),1) == 0 && itemDeg(i) > -1

				// userState(:,2)==i) is make a column vector of length numRows and where values in column are 0 or 1 (1 if userState.get(i, 2) == i)
				// find on top of that gets the rowNum where the column vector has value of 1 (if columnVector.get(i) == 1, return i)
				// then get size of the 
				DenseVector userItems = userState.column(1);
				boolean found = false;
				for (double itemId : userItems.getData())
				{
					if (itemId == i)
					{
						found = true;
					}
				}
				if (!found && itemsDeg.get(i, 0) > -1 && recomNum < itemProb.numRows)
				{
					//				         recomNum = recomNum + 1;

					//						 System.out.println("recomNum = " + recomNum + ", itemProb.numRows = " + itemProb.numRows);

					//						 System.out.println(itemProb.toString());
					//				         itemProb(recomNum,2) = 0;
					itemProb.set(recomNum, 1, 0);
					//				         itemProb(recomNum,1) = i;
					itemProb.set(recomNum, 0, i);

					//				         for j=1:stateSize
					for (int j = 0; j < stateSize; j++)
					{
						//				             subState = userState(j,2);
						int subState = (int) userState.get(j, 1);
						//				             if itemDeg(subState,1) > 0
						if (itemsDeg.get(subState, 0) > 0)
						{
							//				                 if userState(j,3) > userMean
							if (userState.get(j, 2) > userMean)
							{
								//				                    itemProb(recomNum,2) = itemProb(recomNum,2) + targetGraphPP(subState, i);%*exp(-(1/50)*itemDeg(subState,1));%* exp(-(1/50)*j);%*(itemDeg(subState,1));%* exp(-(1/20)*j);
								itemProb.set(recomNum, 1, itemProb.get(recomNum, 1) + targetGraphPP.get(subState, i));
								//		                    itemProb(recomNum,2) = itemProb(recomNum,2) - targetGraphPN(subState, i);%*exp(-(1/50)*itemDeg(subState,1));%* exp(-(1/50)*j);%*(itemDeg(subState,1));%* exp(-(1/20)*j); 
								itemProb.set(recomNum, 1, itemProb.get(recomNum, 1) - targetGraphPN.get(subState, i));
							}
							//		                 else
							else
							{
								//				                    itemProb(recomNum,2) = itemProb(recomNum,2) + targetGraphNP(subState, i);%*exp(-(1/50)*itemDeg(subState,1));%* exp(-(1/50)*j);%*(itemDeg(subState,1));%* exp(-(1/20)*j);
								itemProb.set(recomNum, 1, itemProb.get(recomNum, 1) + targetGraphNP.get(subState, i));
								//itemProb(recomNum,2) = itemProb(recomNum,2) - targetGraphNN(subState, i);%*exp(-(1/50)*itemDeg(subState,1));%* exp(-(1/50)*j);%*(itemDeg(subState,1));%* exp(-(1/20)*j); 
								itemProb.set(recomNum, 1, itemProb.get(recomNum, 1) - targetGraphNN.get(subState, i));
							}
							//		                 end
							//				             end
						}
						//				         end
					}
					//				         if itemProb(recomNum,2) == 0
					if (itemProb.get(recomNum, 1) == 0.0)
					{
						//				             itemProb(recomNum,2) = -1;
						itemProb.set(recomNum, 1, -1.0);
					}
					//				         end
					//				         itemProb(recomNum,2) = (itemProb(recomNum,2)+1)/2;%.*  (min(itemDeg(i,1)/itemMeanDeg,1)^(1/100));
					itemProb.set(recomNum, 1, (itemProb.get(recomNum, 1) + 1.0) / 2.0);
					recomNum++;
					//				     end
				}
				//				 end
				//				 %recoms = sortrows(itemProb,-2);
				//				 recoms = itemProb(1:recomNum,:);

				//				 end
			} 
			itemProbsMap.put(entry.getKey(), itemProb);
		}
	}
	
	private void combRank()
	{
		
	}
	
	private void makeVectorForEachUser()
	{
		for(int userID = 0; userID < numUsers; userID++)
		{
			String s = trainMatrix.getColumnsSet(userID).toString();
			System.out.println(s);
		}
	}
	
	private DenseMatrix getSortedMatrix(DenseMatrix mat, int column)
    {
    	DenseMatrix sortedMatrix = new DenseMatrix(mat.numRows, mat.numColumns);
    	DenseVector vector = mat.column(column);
    	//      double[] d = Collections.sort(vector.getData());
    	//      List<Double> list = new ArrayList<>();
    	TreeMap<Double, Integer> map = new TreeMap<>();
    	int row = 0;
    	for(double d : vector.getData())
    	{
    		//      	list.add(d);
    		map.put(d, row++);
    	}
    	row = 0;
    	//    int column = 0;
    	for(double d : map.navigableKeySet())
    	{
    		sortedMatrix.setRow(row++, mat.row(map.get(d)));//(row, vals);.set(matrix.row(map.get(d)), map.get(d), d);
    	}
    	return sortedMatrix;
    }

	/**
//	function [targetGraphPP, targetGraphNP, targetGraphNN, targetGraphPN] = generateG2(inputData)
	private void generateTwo()
	{
//		sortedData = sortrows(inputData, 1); // already sorted
//		targetData = sortedData; // already sorted
		
//			itemNum = max(targetData(:,2)); // already have
//			dataSize = size(targetData,1); // already have
		
//			targetGraphPP = zeros(itemNum);
//			targetGraphNP = zeros(itemNum);
//			targetGraphPN = zeros(itemNum);
//			targetGraphNN = zeros(itemNum);
			DenseMatrix targetGraphPP = new DenseMatrix(numItems, numItems);
			DenseMatrix targetGraphNP = new DenseMatrix(numItems, numItems);
			DenseMatrix targetGraphPN = new DenseMatrix(numItems, numItems);
			DenseMatrix targetGraphNN = new DenseMatrix(numItems, numItems);
//			userNum = max(targetData(:,1));
//
//			for i=1:userNum
//			    userData = targetData(targetData(:,1)==i,:);
//			    userMean = mean(userData(:,3));
//			    userDegree = size(userData,1);
//			    userData = sortrows(userData,4);
//			    for j=1:(userDegree-1)
//			        if userData(j+1,3) > userMean && userData(j,3) > userMean
//			            targetGraphPP(userData(j,2),userData(j+1,2)) = targetGraphPP(userData(j,2),userData(j+1,2)) + 1;
//			        elseif userData(j+1,3) < userMean && userData(j,3) > userMean
//			            targetGraphPN(userData(j,2),userData(j+1,2)) = targetGraphPN(userData(j,2),userData(j+1,2)) + 1;
//			        elseif userData(j+1,3)> userMean && userData(j,3) < userMean
//			            targetGraphNP(userData(j,2),userData(j+1,2)) = targetGraphNP(userData(j,2),userData(j+1,2)) + 1;
//			        else
//			            targetGraphNN(userData(j,2),userData(j+1,2)) = targetGraphNN(userData(j,2),userData(j+1,2)) + 1;
//			        end
//			    end
//			end
//

//			inDegPP = sum(targetGraphPP)';
//			inDegPN = sum(targetGraphPN)';
//			inDegNP = sum(targetGraphNP)';
//			inDegNN = sum(targetGraphNN)';
//			inDegP = inDegPP + inDegNP;
//			inDegN = inDegNN + inDegPN;
//
//			for i=1:itemNum

//			    if inDegP(i,1)~=0
//			        targetGraphPP(:,i) = targetGraphPP(:,i) / inDegP(i,1);
//			    end
//			    if inDegN(i,1)~=0
//			        targetGraphPN(:,i) = targetGraphPN(:,i) / inDegN(i,1);
//			    end
//			    if inDegP(i,1)~=0
//			        targetGraphNP(:,i) = targetGraphNP(:,i) / inDegP(i,1);
//			    end
//			    if inDegN(i,1)~=0
//			        targetGraphNN(:,i) = targetGraphNN(:,i) / inDegN(i,1);
//			    end
//			end
//

//			end
	}
	**/
	

}
