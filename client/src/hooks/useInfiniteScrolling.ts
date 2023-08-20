import { useState, useEffect } from 'react'
import { getPlanners } from '../apis/planner'
import axios, { Canceler } from 'axios'

type ResultsType = {
  plannerId: number
  planTitle: string
  startDate: string
  endDate: string
  hostName: string
  hostUrl: string
}[]

type DataType = {
  content: ResultsType
  currentPageNumber: number
  totalPageNumber: number
}

const useInfiniteScrolling = (page = 0, query = '') => {
  const [results, setResults] = useState<ResultsType>([])
  const [isLoading, setIsLoading] = useState(false)
  const [isError, setIsError] = useState(false)
  const [hasNextPage, setHasNextPage] = useState(false)

  useEffect(() => {
    setResults([])
  }, [query])

  useEffect(() => {
    setIsLoading(true)
    setIsError(false)

    let cancel: Canceler
    const options = {
      params: { planTitle: query, page },
      cancelToken: new axios.CancelToken(c => (cancel = c)),
    }

    getPlanners(options)
      .then(res => {
        const { content, currentPageNumber, totalPageNumber } = res?.data as DataType
        setResults(prev => [...prev, ...content])
        setHasNextPage(currentPageNumber < totalPageNumber - 1)
        setIsLoading(false)
        setIsError(false)
      })
      .catch(error => {
        if (axios.isCancel(error)) return
        setIsLoading(false)
        setIsError(true)
      })

    return () => cancel()
  }, [page, query])

  return { results, isLoading, hasNextPage, isError }
}

export default useInfiniteScrolling
