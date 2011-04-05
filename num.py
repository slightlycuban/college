import math

# Error list creation function. Add error from p0 & p1 to errList.
# errList is two-dimensional: it has 3 lists for the 3 errors
def listErrors( p1, p0, errList ):
	# Create our list if we're starting out
	if ( errList is None ):
		errList = [[],[],[]]
	
	errList[0].append( error1(p1,p0) )
	errList[1].append( error2(p1,p0) )
	errList[2].append( error3(p1) )
	
	return errList

# Error function #1, returns (p1 - p0)/(p1)
def error1( p1, p0 ):
	return math.fabs( p1 - p0 ) / p1

# Error function #2, returns p1 - p0
def error2( p1, p0 ):
	return math.fabs( p1 - p0 )

# Error function #3, returns computeFunction( p ). This should approach 0.
def error3( p ):
	return math.fabs(computeFunction(p))

#compute the number of iterations need for bisection
def num( ):
	return math.ceil(math.log10(1/.000001)/math.log10(2))

# Calculates the point f(x) = 0 using secant method
# Return a list of the answer and lists of our errors as we get close to the answer
def secant( p0, p1, tol, n ):
	p = 0
	errList = [[],[],[]]

	q0 = computeFunction( p0 )
	q1 = computeFunction( p1 )

	for i in range(2, n + 1):

		# Here we calculate the secant
		p = p1 - (q1 * ((p1 - p0) / (q1 - q0)))

		# Update our error list
		listErrors( p, p1, errList )

		# Check to see if we're within our tolerance
		if math.fabs(p - p1) < tol:
			return [p, errList]

		p0 = p1
		q0 = q1
		p1 = p
		q1 = computeFunction( p )

	return False
	#raise NameError('Completed ' + n + ' iterations, but ' + rvalue + ' is outside our tolerance.')

def isNewtonDone( plast, pnow ):
	result = math.fabs(pnow - plast)/math.fabs(pnow)
	
	if result < .000001:
		return True
	else:
		return False
def newton():
	p = 2
	errList = [[],[],[]]
	cond = False
	while not cond:
		pNew = p - (computeFunction( p ) / computeDerivative( p ))
		cond = isNewtonDone( p , pNew)

		listErrors( pNew, p, errList )
		
		if cond == True:
			return [pNew, errList]
		else:
			p = pNew 	
			
#function equals f(x) = x^4 -3x-3
def computeFunction( input ):
	return (math.pow(input, 4) - (3 * math.pow(input,2)) - 3)

def fixedPoint():
	num = 15
	result = 2
	errList = [[],[],[]]
	
	for i in range(0,num):
		temp =  math.pow(((3 * math.pow(result,2)) + 3), .25)
		listErrors( temp, result, errList )
		result = temp

	return [result, errList]

def bisection( a, b ):
	counter = math.trunc(num())
	p = .5 * ( a + b)
	tempa = 0
	tempp = 0

	errList = [[],[],[]]

	for i in range(0,counter):
		#magic happens here
		tempa = computeFunction(a)
		tempp = computeFunction(p)
		
		if tempa * tempp < 0:
			#is this by value?
			b = p
			p = .5 * (a + b)
			listErrors( p, b, errList )
		else:
			a = p
			p = .5 * (a + b)
			listErrors( p, a, errList )
	return [p, errList]

#the derivative equals 4x^3 - 6x
def computeDerivative( input ):
	return (4 * math.pow(input, 3) - 6 * input)					

if __name__ == "__main__":
	print  bisection( 1, 2)
	print newton()
	print fixedPoint()
	print secant( 1, 2, .000001, 10)
