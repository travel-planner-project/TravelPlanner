import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import PlannerTitle from '../components/Feed/PlannerTitle'
import SearchBar from '../components/Feed/SearchBar'
import styles from './Feed.module.scss'

let data = [
  {
    plannerId: 1,
    planTitle: '부산 여행 가기',
    startDate: '2023-08-16T04:37:23.506Z',
    endDate: '2023-08-17T04:37:23.506Z',
    hostName: '준형',
    hostUrl: '',
    // 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ4HbqZyTk4fRBYWt-7H6ubyM0ex6A8WyVunKD2mqOAmA&s',
  },
  {
    plannerId: 2,
    planTitle: '제주 여행 가기',
    startDate: '2023-08-16T04:37:23.506Z',
    endDate: '2023-08-16T04:37:23.506Z',
    hostName: '준형',
    hostUrl:
      'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ4HbqZyTk4fRBYWt-7H6ubyM0ex6A8WyVunKD2mqOAmA&s',
  },
]

type DatasType = {
  plannerId: number
  planTitle: string
  startDate: string
  endDate: string
  hostName: string
  hostUrl: string
}[]

export default function Feed() {
  const [datas, setDatas] = useState<DatasType>([])

  useEffect(() => {
    setDatas(data)
  }, [])
  return (
    <div className={styles.container}>
      <h1 className={styles.headline}>다른 사람들의 여행 계획을 둘러보세요!</h1>
      <div className={styles.line} />
      <SearchBar />
      <ul>
        {datas.map(data => (
          <Link to={`/plandetail/${data.plannerId}`} key={data.plannerId}>
            <PlannerTitle {...data} key={data.plannerId} />
          </Link>
        ))}
      </ul>
    </div>
  )
}
