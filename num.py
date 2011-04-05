import math

#compute the number of iterations need for bisection
def num( ):
	return math.ceil(math.log10(1/.000001)/math.log10(2))

# Return the point around p1, p0, within tol and n iterations
def secant( p0, p1, tol, n ):
	rvalue = 0

	q0 = computeFunction( p0 )
	q1 = computeFunction( p1 )

	for i in range(2, n + 1):

		# Here we calculate the secant
		rvalue = p1 - (q1 * ((p1 - p0) / (q1 - q0)))

		# Check to see if we're within our tolerance
		if math.fabs(rvalue - p1) < tol:
			return rvalue

		p0 = p1
		q0 = q1
		p1 = rvalue
		q1 = computeFunction( rvalue )

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
	cond = False
	while not cond:
		pNew = p - (computeFunction( p ) / computeDerivative( p ))
		cond = isNewtonDone( p , pNew)
		
		if cond == True:
			return pNew
		else:
			p = pNew 	
			
#function equals f(x) = x^4 -3x-3
def computeFunction( input ):
	return (math.pow(input, 4) - (3 * math.pow(input,2)) - 3)

def fixedPoint():
	num = 15
	result = 2
	
	for i in range(0,num):
		result =  math.pow(((3 * math.pow(result,2)) + 3), .25)

	return result

def bisection( a, b ):
	counter = math.trunc(num())
	p = .5 * ( a + b)
	tempa = 0
	tempp = 0

	for i in range(0,counter):
		#magic happens here
		tempa = computeFunction(a)
		tempp = computeFunction(p)
		
		if tempa * tempp < 0:
			#is this by value?
			b = p
			p = .5 * (a + b)
		else:
			a = p
			p = .5 * (a + b)
	return p

#the derivative equals 4x^3 - 6x
def computeDerivative( input ):
	return (4 * math.pow(input, 3) - 6 * input)					
if __name__ == "__main__":
	print  bisection( 1, 2)
	print newton()
	print fixedPoint()
	print secant( 1, 2, .000001, 10)
